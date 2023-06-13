package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/** This class was created to read files from FirstLevelDivisions database. */
public class FirstLevelDivisionsDaoImpl {


    /** This method retrieves all FirstLevelDivisions from database
     * @return allFirstLevelDivisions
     */
    public static ObservableList<FirstLevelDivisions> getAllFirstLevelDivisions()throws SQLException {

        ObservableList<FirstLevelDivisions> allFirstLevelDivisions = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int divisionID = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int countryID = rs.getInt("Country_ID");

            FirstLevelDivisions firstLevelDivisions = new FirstLevelDivisions(divisionID, division, createDate, createdBy, lastUpdate, lastUpdatedBy, countryID);

            allFirstLevelDivisions.add(firstLevelDivisions);

        }
        return allFirstLevelDivisions;
    }

    /** This method retrieves all US divisions from database
     * @return USDivisions
     */
    public static ObservableList<FirstLevelDivisions> getAllUSDivisions()throws SQLException {

        ObservableList<FirstLevelDivisions> USDivisions = FXCollections.observableArrayList();

        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 1";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){

            int divisionID = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int countryID = rs.getInt("Country_ID");
            FirstLevelDivisions USFirstLevelDivisions = new FirstLevelDivisions(divisionID, division, createDate, createdBy, lastUpdate, lastUpdatedBy, countryID);

            USDivisions.add(USFirstLevelDivisions);

        }
        return USDivisions;
    }

    /** This method retrieves all UK divisions from database
     * @return UKDivisions
     */
    public static ObservableList<FirstLevelDivisions> getAllUKDivisions()throws SQLException {

        ObservableList<FirstLevelDivisions> UKDivisions = FXCollections.observableArrayList();

        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 2";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){

            int divisionID = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int countryID = rs.getInt("Country_ID");
            FirstLevelDivisions UKFirstLevelDivisions = new FirstLevelDivisions(divisionID, division, createDate, createdBy, lastUpdate, lastUpdatedBy, countryID);

            UKDivisions.add(UKFirstLevelDivisions);

        }
        return UKDivisions;
    }

    /** This method retrieves all Canada divisions from database
     * @return canadaDivisions
     */
    public static ObservableList<FirstLevelDivisions> getAllCanadaDivisions()throws SQLException {

        ObservableList<FirstLevelDivisions> canadaDivisions = FXCollections.observableArrayList();

        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 3";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){

            int divisionID = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int countryID = rs.getInt("Country_ID");
            FirstLevelDivisions canadaFirstLevelDivisions = new FirstLevelDivisions(divisionID, division, createDate, createdBy, lastUpdate, lastUpdatedBy, countryID);

            canadaDivisions.add(canadaFirstLevelDivisions);

        }
        return canadaDivisions;
    }

    /** This method retrieves division names by country ID
     * @param countryId
     * @return divisionName
     */
    public static ObservableList<FirstLevelDivisions> getDivisionByCountryID(int countryId)throws SQLException {

        ObservableList<FirstLevelDivisions> divisionName = FXCollections.observableArrayList();

        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, countryId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){

            int divisionID = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int countryID = rs.getInt("Country_ID");
            FirstLevelDivisions firstLevelDivisionName = new FirstLevelDivisions(divisionID, division, createDate, createdBy, lastUpdate, lastUpdatedBy, countryID);

            divisionName.add(firstLevelDivisionName);

        }
        return divisionName;
    }

    /** This method retrieves division names by division ID
     * @param divisionId
     * @return firstLevelDivisionName
     */
    public static FirstLevelDivisions getDivisionByDivisionID(int divisionId)throws SQLException {

        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){

            int divisionID = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int countryID = rs.getInt("Country_ID");

            FirstLevelDivisions firstLevelDivisionName = new FirstLevelDivisions(divisionID, division, createDate, createdBy, lastUpdate, lastUpdatedBy, countryID);

            return firstLevelDivisionName;

        }
        return null;
    }

    /** This method retrieves country ID by division ID
     * @param divisionID
     * @return countryID
     */
    public static int getCountryIDByDivisionID(int divisionID) throws SQLException {
        int countryID = -1;
        String sql = "SELECT Country_ID FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, divisionID);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            countryID = rs.getInt("Country_ID");
        }
        return countryID;
    }
}
