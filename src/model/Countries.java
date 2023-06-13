package model;

import java.time.LocalDateTime;

/** This class was created to define all data types and methods of countries, according to the Database ERD. */
public class Countries {

    private int countryID;
    private String country;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;

    public Countries(int countryID, String country, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) {
        this.countryID = countryID;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
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

    /** This is the country getter
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /** This is the country setter
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
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
        return country;
    }
}
