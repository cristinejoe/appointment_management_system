package model;

import java.time.LocalDateTime;

/** This class was created to define all data types and methods of customers, according to the Database ERD. */
public class Customers {

    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int divisionID;

    public Customers(int customerID, String customerName, String address, String postalCode, String phone, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
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

    /** This is the customerName getter
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /** This is the customerName setter
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /** This is the address getter
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /** This is the address setter
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /** This is the postalCode getter
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /** This is the postalCode setter
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /** This is the phone getter
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /** This is the phone setter
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
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

    /** This is the divisionID getter
     * @return the divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /** This is the divisionID setter
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    @Override
    public String toString() {
        return String.valueOf(customerID);
    }
}
