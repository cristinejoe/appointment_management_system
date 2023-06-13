package model;

import java.time.LocalDateTime;

/** This class was created to define all data types and methods of first level divisions, according to the Database ERD. */
public class FirstLevelDivisions {
    private int divisionID;
    private String division;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int countryID;

    public FirstLevelDivisions(int divisionID, String division, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryID = countryID;
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

    /** This is the division getter
     * @return the division
     */
    public String getDivision() {
        return division;
    }

    /** This is the division setter
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
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

    /** This is the countryID getter
     * @return the countryID
     */
    public int getCountryID() {
        return countryID;
    }

    /** This is the countryID setter
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    @Override
    public String toString() {
        return division;
    }
}
