package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Users;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/** This class was created to read and verify user data from database. */
public class UsersDaoImpl {

    /** This method select all users and add them into an observablelist
     * @return users
     */
    public static ObservableList<Users> getUsers()throws SQLException{
        ObservableList<Users> users = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Users";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");

            Users user = new Users(userID, userName, password, createDate, createdBy, lastUpdate, lastUpdatedBy);
            users.add(user);
        }

        return users;
    }

    /** This method checks if provided username and password match with data in the database
     * @param userName
     * @param password
     * @return user or null
     */
    public static Users checkUserCredentials(String userName, String password) throws IOException {
        try{
            String sql = "SELECT * FROM Users WHERE User_Name = ? AND Password = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                int userID = rs.getInt("User_ID");
                String user_name = rs.getString("User_Name");
                String user_password = rs.getString("Password");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");

                Users user = new Users(userID, userName, password, createDate, createdBy, lastUpdate, lastUpdatedBy);
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /** This method retrieves all users by user ID
     * @param userID
     * @return usersById or null
     */
    public static Users getUserByUserID(int userID) throws SQLException {

        String sql = "SELECT * FROM Users where User_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, userID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String userPassword = rs.getString("Password");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");

            Users usersById = new Users(userId, userName, userPassword, createDate, createdBy, lastUpdate, lastUpdatedBy);
            return usersById;
        }

        return null;
    }

}
