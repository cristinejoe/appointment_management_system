package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/** This class was created to read files from Countries database. */
public class CountriesDaoImpl {

    /** This method retrieves all countries from database
     * @return allCountries
     */
    public static ObservableList<Countries> getAllCountries()throws SQLException {

        ObservableList<Countries> allCountries = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Countries";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int countryId = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");

            Countries country = new Countries(countryId, countryName, createDate, createdBy, lastUpdate, lastUpdatedBy);

            allCountries.add(country);

        }
        return allCountries;
    }

    /** This method retrieves countries by country ID
     * @return countryName
     */
    public static Countries getCountryByID(int countryId)throws SQLException {

        String sql = "SELECT * FROM countries WHERE Country_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, countryId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){

            int countryID = rs.getInt("Country_ID");
            String country = rs.getString("Country");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");

            Countries countryName = new Countries(countryID, country, createDate, createdBy, lastUpdate, lastUpdatedBy);

            return countryName;
        }
        return null;
    }


}
