/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2009-2012 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2012 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.netmgt.dao;

import java.util.Collection;
import java.util.Date;

import org.opennms.netmgt.model.DataLinkInterface;
import org.opennms.netmgt.model.OnmsArpInterface.StatusType;

public interface DataLinkInterfaceDao extends OnmsDao<DataLinkInterface, Integer>{
    Collection<DataLinkInterface> findAll(Integer offset, Integer limit);

    Collection<DataLinkInterface> findByNodeId(int nodeId);

    Collection<DataLinkInterface> findByNodeParentId(int nodeParentId);

    void markDeletedIfNodeDeleted();

    DataLinkInterface findByNodeIdAndIfIndex(int nodeId, int ifindex);

    void deactivateIfOlderThan(Date now, String source);

    void deleteIfOlderThan(Date now, String source);

    void setStatusForNode(int nodeid, int parentNodeId, StatusType action);

    void setStatusForNode(final int nodeId, final StatusType action);

    void setStatusForNodeAndIfIndex(int nodeid, int ifIndex, StatusType action);
    
    void setStatusForNode(int nodeid, String source, StatusType action);

    void setStatusForNodeAndIfIndex(int nodeid, int ifIndex, String source, StatusType action);

    Collection<DataLinkInterface> findByNodeIdAndParentId(int nodeId, int nodeParentId);

    Collection<DataLinkInterface> findByNodeIdOrParentId(int nodeId, int nodeParentId);
}
