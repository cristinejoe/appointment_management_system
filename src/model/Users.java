package model;

import java.time.LocalDateTime;

/** This class was created to define all data types and methods of users, according to the Database ERD. */
public class Users {

    private int userID;
    private String userName;
    private String password;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;

    public Users(int userID, String userName, String password, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy){
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
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

    /** This is the userName getter
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /** This is the userName setter
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** This is the password getter
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /** This is the password setter
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return String.valueOf(userID);
    }

}
