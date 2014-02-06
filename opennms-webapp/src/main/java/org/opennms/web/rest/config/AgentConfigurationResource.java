package org.opennms.web.rest.config;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.opennms.core.config.api.ConfigurationResource;
import org.opennms.core.config.api.ConfigurationResourceException;
import org.opennms.core.criteria.CriteriaBuilder;
import org.opennms.core.xml.bind.InetAddressXmlAdapter;
import org.opennms.netmgt.config.collectd.CollectdConfiguration;
import org.opennms.netmgt.config.collectd.Filter;
import org.opennms.netmgt.config.collectd.Service;
import org.opennms.netmgt.dao.api.MonitoredServiceDao;
import org.opennms.netmgt.dao.api.SnmpConfigDao;
import org.opennms.netmgt.filter.FilterDao;
import org.opennms.netmgt.model.OnmsMonitoredService;
import org.opennms.netmgt.snmp.SnmpAgentConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.sun.jersey.api.core.ResourceContext;
import com.sun.jersey.spi.resource.PerRequest;

@Component
@PerRequest
@Scope("prototype")
public class AgentConfigurationResource implements InitializingBean {
    @XmlRootElement(name="agents")
    public static class AgentResponseList extends ArrayList<AgentResponse> {
        private static final long serialVersionUID = -6003061762086860946L;

        @XmlElement(name = "agent")
        public List<AgentResponse> getAgents() {
            return this;
        }

        public void setAgents(List<AgentResponse> agents) {
            if (agents == this) return;
            clear();
            addAll(agents);
        }

        @XmlAttribute(name="count")
        public Integer getCount() {
            return this.size();
        }

        // make JaxbUtils happy (for a getter there must be a setter)
        public void setCount(Integer count) {
            ; // no implementation, because it is calculated
        }
    }

    @XmlRootElement(name="agent")
    public static class AgentResponse {
        private InetAddress m_address;
        private Integer m_port;
        private String m_serviceName;

        public AgentResponse() {
        }

        public AgentResponse(final InetAddress address, final Integer port, final String serviceName) {
            m_address = address;
            m_port = port;
            m_serviceName = serviceName;
        }

        @XmlElement(name="address")
        @XmlJavaTypeAdapter(InetAddressXmlAdapter.class)
        public InetAddress getAddress() {
            return m_address;
        }
        @XmlElement(name="port")
        public Integer getPort() {
            return m_port;
        }
        @XmlElement(name="serviceName")
        public String getServiceName() {
            return m_serviceName;
        }
    }

    private static Logger LOG = LoggerFactory.getLogger(AgentConfigurationResource.class);

    @Resource(name="collectd-configuration.xml")
    private ConfigurationResource<CollectdConfiguration> m_collectdConfigurationResource;

    @Autowired
    private FilterDao m_filterDao;

    @Autowired
    private MonitoredServiceDao m_monitoredServiceDao;

    @Autowired
    private SnmpConfigDao m_snmpConfigDao;

    @Context
    private ResourceContext m_context;

    @Context 
    private UriInfo m_uriInfo;

    public void setCollectdConfigurationResource(final ConfigurationResource<CollectdConfiguration> resource) {
        m_collectdConfigurationResource = resource;
    }

    public void setFilterDao(final FilterDao dao) {
        m_filterDao = dao;
    }

    public void setMonitoredServiceDao(final MonitoredServiceDao dao) {
        m_monitoredServiceDao = dao;
    }

    public void setSnmpConfigDao(final SnmpConfigDao dao) {
        m_snmpConfigDao = dao;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(m_collectdConfigurationResource, "CollectdConfigurationResource must not be null.");
        Assert.notNull(m_filterDao, "FilterDao must not be null.");
        Assert.notNull(m_monitoredServiceDao, "MonitoredServiceDao must not be null.");
        Assert.notNull(m_snmpConfigDao, "SnmpConfigDao must not be null.");
    }

    @GET
    @Path("{filterName}/{serviceName}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML})
    public Response getAgentsForService(@PathParam("filterName") final String filterName, @PathParam("serviceName") final String serviceName) throws ConfigurationResourceException {
        LOG.debug("getAgentsForService(): filterName={}, serviceName={}", filterName, serviceName);

        if (filterName == null || serviceName == null) {
            throw new IllegalArgumentException("You must specify a filter name and service name!");
        }

        final Filter filter = m_collectdConfigurationResource.get().getFilter(filterName);
        if (filter == null) {
            LOG.warn("No filter matching {} could be found.", filterName);
            return Response.status(404).build();
        }

        final List<InetAddress> addresses = m_filterDao.getActiveIPAddressList(filter.getContent());
        LOG.debug("Matched {} IP addresses for filter {}", addresses == null? 0 : addresses.size(), filterName);

        if (addresses == null || addresses.size() == 0) {
            return Response.noContent().build();
        }

        final CriteriaBuilder builder = new CriteriaBuilder(OnmsMonitoredService.class);
        builder.createAlias("ipInterface", "iface");
        builder.createAlias("serviceType", "type");
        builder.in("iface.ipAddress", addresses);
        builder.eq("type.name", serviceName);
        final List<OnmsMonitoredService> services = m_monitoredServiceDao.findMatching(builder.toCriteria());
        int defaultPort = 161;

        // TODO: We shouldn't have to hardcode like this; what's the right way to know the port to return?
        if (serviceName.equals("SNMP")) {
            defaultPort = m_snmpConfigDao.getDefaults().getPort();
        } else {
            final CollectdConfiguration collectdConfiguration = m_collectdConfigurationResource.get();
            org.opennms.netmgt.config.collectd.Package pack = collectdConfiguration.getPackage(filterName);
            if (pack == null) {
                for (final org.opennms.netmgt.config.collectd.Package p : collectdConfiguration.getPackages()) {
                    if (filterName.equals(p.getFilter().getName())) {
                        pack = p;
                        break;
                    }
                }
            }
            if (pack != null) {
                final Service svc = pack.getService(serviceName);
                final String port = svc.getParameter("port");
                if (port != null) {
                    try {
                        defaultPort = Integer.valueOf(port);
                    } catch (final NumberFormatException e) {
                        LOG.debug("Unable to turn port {} from service {} into a number.", port, serviceName);
                    }
                }
            }
        }

        final AgentResponseList responses = new AgentResponseList();

        for (final OnmsMonitoredService service : services) {
            final InetAddress ipAddress = service.getIpAddress();

            int port = defaultPort;
            if ("SNMP".equals(serviceName)) {
                final SnmpAgentConfig config = m_snmpConfigDao.getAgentConfig(ipAddress);
                if (config != null) {
                    port = config.getPort();
                }
            }

            responses.add(new AgentResponse(ipAddress, port, service.getServiceName()));
        }

        return Response.ok(responses).build();
    }
}