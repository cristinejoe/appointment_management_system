package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/** This class was created to read, create, update and delete files from Customers database. */
public class CustomerDaoImpl {

    /** This method retrieves all customers from database
     * @return allCustomers
     */
    public static ObservableList<Customers> getAllCustomers()throws SQLException {

        ObservableList<Customers> allCustomers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customers";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionId = rs.getInt("Division_ID");

            Customers customer = new Customers(customerId, customerName, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId);

            allCustomers.add(customer);

        }
        return allCustomers;
    }

    /** This method adds a new customer to the database
     * @param customerName
     * @param address
     * @param postalCode
     * @param phoneNumber
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param divisionID
     */
    public static void addCustomer(String customerName, String address, String postalCode, String phoneNumber, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionID) throws SQLException {

        String sql = "INSERT INTO customers "
                + "(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setTimestamp(5, createDate);
        ps.setString(6,createdBy);
        ps.setTimestamp(7, lastUpdate);
        ps.setString(8,lastUpdatedBy);
        ps.setInt(9, divisionID);
        ps.executeUpdate();
    }

    /** This method modifies a customer from the database
     * @param customerName
     * @param address
     * @param postalCode
     * @param phoneNumber
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param divisionID
     */
    public static void modifyCustomer(int customerId, String customerName, String address, String postalCode, String phoneNumber, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionID) throws SQLException {

        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ?  WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setTimestamp(5, createDate);
        ps.setString(6, createdBy);
        ps.setTimestamp(7, lastUpdate);
        ps.setString(8,lastUpdatedBy);
        ps.setInt(9, divisionID);
        ps.setInt(10, customerId);
        ps.executeUpdate();
    }

    /** This method deletes a customer from the database
     * @param customerId
     */
    public static void deleteCustomer(int customerId) throws SQLException{

        String sql = "DELETE FROM customers WHERE CUSTOMER_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        ps.executeUpdate();
    }

    /** This method retrieves all customers by customer ID
     * @param customerID
     * @return customersById
     */
    public static Customers getCustomerByCustomerID(int customerID) throws SQLException {

        String sql = "SELECT * FROM Customers where Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionId = rs.getInt("Division_ID");

            Customers customersById = new Customers(customerId, customerName, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId);
            return customersById;
        }

        return null;
    }

}
