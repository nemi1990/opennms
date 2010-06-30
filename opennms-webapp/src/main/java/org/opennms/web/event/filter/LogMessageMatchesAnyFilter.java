//
// This file is part of the OpenNMS(R) Application.
//
// OpenNMS(R) is Copyright (C) 2002-2003 The OpenNMS Group, Inc.  All rights reserved.
// OpenNMS(R) is a derivative work, containing both original code, included code and modified
// code that was published under the GNU General Public License. Copyrights for modified 
// and included code are below.
//
// OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
//
// Modifications:
//
// 2007 Jul 24: Java 5 generics and format code. - dj@opennms.org
// 2004 Feb 11: Change the search string logic from 'OR' to 'AND'.
//
// Copyright (C) 1999-2001 Oculan Corp.  All rights reserved.
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
//
// For more information contact:
//      OpenNMS Licensing       <license@opennms.org>
//      http://www.opennms.org/
//      http://www.opennms.com/
//

package org.opennms.web.event.filter;

import org.opennms.web.filter.SubstringFilter;

/**
 * <p>LogMessageMatchesAnyFilter class.</p>
 *
 * @author <A HREF="mailto:jamesz@opennms.com">James Zuo </A>
 * @version $Id: $
 * @since 1.8.1
 */
public class LogMessageMatchesAnyFilter extends SubstringFilter {
    /** Constant <code>TYPE="msgmatchany"</code> */
    public static final String TYPE = "msgmatchany";

    /**
     * <p>Constructor for LogMessageMatchesAnyFilter.</p>
     *
     * @param substring
     *            a space-delimited list of search substrings
     */
    public LogMessageMatchesAnyFilter(String substring) {
        super(TYPE, "EVENTLOGMSG", "eventLogMsg", substring);
    }

    /**
     * <p>getTextDescription</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getTextDescription() {
        StringBuffer buffer = new StringBuffer("message containing \"");
        buffer.append(getValue());
        buffer.append("\"");

        return buffer.toString();
    }

    /**
     * <p>toString</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String toString() {
        return "<LogMessageMatchesAnyFilter: " + this.getDescription() + ">";
    }

    /** {@inheritDoc} */
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }

}
