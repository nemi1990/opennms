//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.3-b01-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.01.29 at 01:15:48 PM EST 
//


package org.opennms.netmgt.provision.persist.requisition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.opennms.core.utils.ThreadCategory;
import org.opennms.netmgt.provision.persist.OnmsNodeRequisition;
import org.opennms.netmgt.provision.persist.RequisitionVisitor;


/**
 * <p>Requisition class.</p>
 *
 * @author ranger
 * @version $Id: $
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="model-import")
public class Requisition implements Serializable, Comparable<Requisition> {
    private static final long serialVersionUID = 1L;

    @XmlTransient
    private Map<String, OnmsNodeRequisition> m_nodeReqs = new LinkedHashMap<String, OnmsNodeRequisition>();
    
    @XmlElement(name="node")
    protected List<RequisitionNode> m_nodes = new ArrayList<RequisitionNode>();
    
    @XmlAttribute(name="date-stamp")
    protected XMLGregorianCalendar m_dateStamp;
    
    @XmlAttribute(name="foreign-source")
    protected String m_foreignSource = "imported:";
    
    @XmlAttribute(name="last-import")
    protected XMLGregorianCalendar m_lastImport;

    /**
     * <p>getNode</p>
     *
     * @param foreignId a {@link java.lang.String} object.
     * @return a {@link org.opennms.netmgt.provision.persist.requisition.RequisitionNode} object.
     */
    public RequisitionNode getNode(String foreignId) {
        if (m_nodes != null) {
            for (RequisitionNode n : m_nodes) {
                if (n.getForeignId().equals(foreignId)) {
                    if (log().isDebugEnabled()) {
                        log().debug(String.format("returning node '%s' for foreign id '%s'", n, foreignId));
                    }
                    return n;
                }
            }
        }
        return null;
    }

    /**
     * <p>removeNode</p>
     *
     * @param node a {@link org.opennms.netmgt.provision.persist.requisition.RequisitionNode} object.
     */
    public void removeNode(RequisitionNode node) {
        if (m_nodes != null) {
            Iterator<RequisitionNode> i = m_nodes.iterator();
            while (i.hasNext()) {
                RequisitionNode n = i.next();
                if (n.getForeignId().equals(node.getForeignId())) {
                    i.remove();
                    break;
                }
            }
        }
    }

    /**
     * <p>deleteNode</p>
     *
     * @param foreignId a {@link java.lang.String} object.
     */
    public void deleteNode(String foreignId) {
        if (m_nodes != null) {
            Iterator<RequisitionNode> i = m_nodes.iterator();
            while (i.hasNext()) {
                RequisitionNode n = i.next();
                if (n.getForeignId().equals(foreignId)) {
                    i.remove();
                    break;
                }
            }
        }
    }

    /* backwards-compat with ModelImport */
    /**
     * <p>getNode</p>
     *
     * @return an array of {@link org.opennms.netmgt.provision.persist.requisition.RequisitionNode} objects.
     */
    @XmlTransient
    public RequisitionNode[] getNode() {
        return getNodes().toArray(new RequisitionNode[] {});
    }

    /**
     * <p>getNodes</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<RequisitionNode> getNodes() {
        return m_nodes;
    }

    /**
     * <p>setNodes</p>
     *
     * @param nodes a {@link java.util.List} object.
     */
    public void setNodes(List<RequisitionNode> nodes) {
        m_nodes = nodes;
        updateNodeCache();
    }

    /**
     * <p>insertNode</p>
     *
     * @param node a {@link org.opennms.netmgt.provision.persist.requisition.RequisitionNode} object.
     */
    public void insertNode(RequisitionNode node) {
        if (m_nodeReqs.containsKey(node.getForeignId())) {
            RequisitionNode n = m_nodeReqs.get(node.getForeignId()).getNode();
            m_nodes.remove(n);
        }
        m_nodes.add(0, node);
        m_nodeReqs.put(node.getForeignId(), new OnmsNodeRequisition(getForeignSource(), node));
    }

    /**
     * <p>putNode</p>
     *
     * @param node a {@link org.opennms.netmgt.provision.persist.requisition.RequisitionNode} object.
     */
    public void putNode(RequisitionNode node) {
        if (m_nodeReqs.containsKey(node.getForeignId())) {
            RequisitionNode n = m_nodeReqs.get(node.getForeignId()).getNode();
            m_nodes.remove(n);
        }
        m_nodes.add(node);
        m_nodeReqs.put(node.getForeignId(), new OnmsNodeRequisition(getForeignSource(), node));
    }

    /**
     * <p>getDateStamp</p>
     *
     * @return a {@link javax.xml.datatype.XMLGregorianCalendar} object.
     */
    public XMLGregorianCalendar getDateStamp() {
        return m_dateStamp;
    }

    /**
     * <p>setDateStamp</p>
     *
     * @param value a {@link javax.xml.datatype.XMLGregorianCalendar} object.
     */
    public void setDateStamp(XMLGregorianCalendar value) {
        m_dateStamp = value;
    }

    /**
     * <p>updateDateStamp</p>
     */
    public void updateDateStamp() {
        try {
            m_dateStamp = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
        } catch (DatatypeConfigurationException e) {
            log().warn("unable to update datestamp", e);
        }
    }

    /**
     * <p>getForeignSource</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getForeignSource() {
        if (m_foreignSource == null) {
            return "imported:";
        } else {
            return m_foreignSource;
        }
    }

    /**
     * <p>setForeignSource</p>
     *
     * @param value a {@link java.lang.String} object.
     */
    public void setForeignSource(String value) {
        m_foreignSource = value;
    }

    /**
     * <p>getLastImport</p>
     *
     * @return a {@link javax.xml.datatype.XMLGregorianCalendar} object.
     */
    public XMLGregorianCalendar getLastImport() {
        return m_lastImport;
    }

    /**
     * <p>setLastImport</p>
     *
     * @param value a {@link javax.xml.datatype.XMLGregorianCalendar} object.
     */
    public void setLastImport(XMLGregorianCalendar value) {
        m_lastImport = value;
    }

    /**
     * Update the last imported stamp to the current date and time
     */
    public void updateLastImported() {
        try {
            m_lastImport = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
        } catch (DatatypeConfigurationException e) {
            log().warn("unable to update last import datestamp", e);
        }
    }

    /* Start non-JAXB methods */

    /**
     * <p>Constructor for Requisition.</p>
     */
    public Requisition() {
        updateNodeCache();
        updateDateStamp();
    }

    /**
     * <p>Constructor for Requisition.</p>
     *
     * @param foreignSource a {@link java.lang.String} object.
     */
    public Requisition(String foreignSource) {
        this();
        m_foreignSource = foreignSource;
    }
    
    private void updateNodeCache() {
        m_nodeReqs.clear();
        if (m_nodes != null) {
            for (RequisitionNode n : m_nodes) {
                m_nodeReqs.put(n.getForeignId(), new OnmsNodeRequisition(getForeignSource(), n));
            }
        }
    }
    
    /**
     * <p>visit</p>
     *
     * @param visitor a {@link org.opennms.netmgt.provision.persist.RequisitionVisitor} object.
     */
    public void visit(RequisitionVisitor visitor) {
        if (m_nodeReqs.size() == 0 && m_nodes != null && m_nodes.size() > 0) {
            updateNodeCache();
        }

        if (visitor == null) {
            log().warn("no visitor specified!");
            return;
        }

        visitor.visitModelImport(this);
        
        for (OnmsNodeRequisition nodeReq : m_nodeReqs.values()) {
            nodeReq.visit(visitor);
        }
        
        visitor.completeModelImport(this);
    }

    /**
     * <p>getNodeRequistion</p>
     *
     * @param foreignId a {@link java.lang.String} object.
     * @return a {@link org.opennms.netmgt.provision.persist.OnmsNodeRequisition} object.
     */
    public OnmsNodeRequisition getNodeRequistion(String foreignId) {
        if (m_nodeReqs.size() == 0 && m_nodes != null && m_nodes.size() > 0) {
            updateNodeCache();
        }
        return m_nodeReqs.get(foreignId);
    }
    
    /**
     * <p>getNodeCount</p>
     *
     * @return a int.
     */
    @XmlTransient
    public int getNodeCount() {
        return (m_nodes == null) ? 0 : m_nodes.size();
    }

    private ThreadCategory log() {
        return ThreadCategory.getInstance(Requisition.class);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("foreign-source", getForeignSource())
            .append("date-stamp", getDateStamp())
            .append("last-import", getLastImport())
            .append("nodes", getNodes())
            .toString();
    }

    /**
     * <p>compareTo</p>
     *
     * @param obj a {@link org.opennms.netmgt.provision.persist.requisition.Requisition} object.
     * @return a int.
     */
    public int compareTo(Requisition obj) {
        return new CompareToBuilder()
            .append(getForeignSource(), obj.getForeignSource())
            .append(getDateStamp(), obj.getDateStamp())
            .append(getLastImport(), obj.getLastImport())
            .append(getNodes(), obj.getNodes())
            .toComparison();
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Requisition) {
            Requisition other = (Requisition) obj;
            return new EqualsBuilder()
                .append(getForeignSource(), other.getForeignSource())
                /*
                .append(getDateStamp(), other.getDateStamp())
                .append(getLastImport(), other.getLastImport())
                .append(getNodes(), other.getNodes())
                */
                .isEquals();
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(37, 29)
            .append(getForeignSource())
            .append(getDateStamp())
            .append(getLastImport())
            .append(getNodes())
            .toHashCode();
      }

}
