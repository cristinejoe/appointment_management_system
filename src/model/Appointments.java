package model;

import java.time.LocalDateTime;

/** This class was created to define all data types and methods of appointments, according to the Database ERD. */
public class Appointments {

    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int customerID;
    private int userID;
    private int contactID;

    public Appointments(int appointmentID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /** This is the appointmentID getter
     * @return the appointmentID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /** This is the appointmentID setter
     * @param appointmentID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /** This is the title getter
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /** This is the title setter
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /** This is the description getter
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /** This is the description setter
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /** This is the location getter
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /** This is the location setter
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /** This is the type getter
     * @return the type
     */
    public String getType() {
        return type;
    }

    /** This is the type setter
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /** This is the start getter
     * @return the start
     */
    public LocalDateTime getStart() {
        return start;
    }

    /** This is the start setter
     * @param start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /** This is the end getter
     * @return the end
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /** This is the end setter
     * @param end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /** This is the createDate getter
     * @return the createDate
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /** This is the createDate setter
     * @param createDate
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /** This is the createdBy getter
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /** This is the createdBy setter
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** This is the lastUpdate getter
     * @return the lastUpdate
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /** This is the lastUpdate setter
     * @param lastUpdate
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /** This is the lastUpdatedBy getter
     * @return the lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /** This is the lastUpdatedBy setter
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /** This is the customerID getter
     * @return the customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /** This is the customerID setter
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /** This is the userID getter
     * @return the userID
     */
    public int getUserID() {
        return userID;
    }

    /** This is the userID setter
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /** This is the contactID getter
     * @return the contactID
     */
    public int getContactID() {
        return contactID;
    }

    /** This is the contactID setter
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    @Override
    public String toString() {
        return type;
    }

}