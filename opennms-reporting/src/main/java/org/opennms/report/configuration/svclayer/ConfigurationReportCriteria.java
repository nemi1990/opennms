package org.opennms.report.configuration.svclayer;

import java.util.Date;

/**
 * <p>ConfigurationReportCriteria class.</p>
 *
 * @author ranger
 * @version $Id: $
 */
public class ConfigurationReportCriteria {

    String theDate;
    String reportFormat;
    String reportEmail;
    String user;
    Date reportRequestDate;

    /**
     * <p>Getter for the field <code>theDate</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getTheDate() {
        return theDate;
    }

    /**
     * <p>Setter for the field <code>theDate</code>.</p>
     *
     * @param theDate a {@link java.lang.String} object.
     */
    public void setTheDate(String theDate) {
        this.theDate = theDate;
    }

    /**
     * <p>Getter for the field <code>reportFormat</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getReportFormat() {
        return reportFormat;
    }

    /**
     * <p>Setter for the field <code>reportFormat</code>.</p>
     *
     * @param reportFormat a {@link java.lang.String} object.
     */
    public void setReportFormat(String reportFormat) {
        this.reportFormat = reportFormat;
    }

    /**
     * <p>Getter for the field <code>reportEmail</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getReportEmail() {
        return reportEmail;
    }

    /**
     * <p>Setter for the field <code>reportEmail</code>.</p>
     *
     * @param reportEmail a {@link java.lang.String} object.
     */
    public void setReportEmail(String reportEmail) {
        this.reportEmail = reportEmail;
    }

    /**
     * <p>Getter for the field <code>user</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getUser() {
        return user;
    }

    /**
     * <p>Setter for the field <code>user</code>.</p>
     *
     * @param user a {@link java.lang.String} object.
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * <p>Getter for the field <code>reportRequestDate</code>.</p>
     *
     * @return a {@link java.util.Date} object.
     */
    public Date getReportRequestDate() {
        return reportRequestDate;
    }

    /**
     * <p>Setter for the field <code>reportRequestDate</code>.</p>
     *
     * @param reportRequestDate a {@link java.util.Date} object.
     */
    public void setReportRequestDate(Date reportRequestDate) {
        this.reportRequestDate = reportRequestDate;
    }

    /**
     * <p>Constructor for ConfigurationReportCriteria.</p>
     *
     * @param _date a {@link java.lang.String} object.
     * @param _format a {@link java.lang.String} object.
     * @param _reportemail a {@link java.lang.String} object.
     * @param _user a {@link java.lang.String} object.
     * @param _reportRequestDate a {@link java.util.Date} object.
     */
    public ConfigurationReportCriteria(String _date, String _format, String _reportemail, String _user, Date _reportRequestDate){
        theDate = _date;
        reportFormat = _format;
        reportEmail = _reportemail;
        user=_user;
        reportRequestDate=_reportRequestDate;
        
    }
    
    /**
     * <p>Constructor for ConfigurationReportCriteria.</p>
     */
    public ConfigurationReportCriteria() {
        
    }

}
