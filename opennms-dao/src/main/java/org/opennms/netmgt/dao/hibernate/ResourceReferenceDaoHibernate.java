/*
 * This file is part of the OpenNMS(R) Application.
 *
 * OpenNMS(R) is Copyright (C) 2006 The OpenNMS Group, Inc.  All rights reserved.
 * OpenNMS(R) is a derivative work, containing both original code, included code and modified
 * code that was published under the GNU General Public License. Copyrights for modified
 * and included code are below.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Modifications:
 *
 * 2007 Apr 10: Created this file.
 *
 * Copyright (C) 2007 The OpenNMS Group, Inc.  All rights reserved.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * For more information contact:
 *      OpenNMS Licensing       <license@opennms.org>
 *      http://www.opennms.org/
 *      http://www.opennms.com/
 */
package org.opennms.netmgt.dao.hibernate;

import org.opennms.netmgt.dao.ResourceReferenceDao;
import org.opennms.netmgt.model.ResourceReference;

/**
 * DAO implementation for accessing ResourceReference objects.
 *
 * @author <a href="mailto:dj@opennms.org">DJ Gregor</a>
 * @see ResourceReference
 * @version $Id: $
 */
public class ResourceReferenceDaoHibernate extends AbstractDaoHibernate<ResourceReference, Integer> implements ResourceReferenceDao {

	/**
	 * <p>Constructor for ResourceReferenceDaoHibernate.</p>
	 */
	public ResourceReferenceDaoHibernate() {
		super(ResourceReference.class);
	}

    /** {@inheritDoc} */
    public ResourceReference getByResourceId(String resourceId) {
        return findUnique("from ResourceReference where resourceId = ?", resourceId);
    }

}
