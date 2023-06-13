package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/** This class was created to read, create, update and delete files from appointments database. */
public class AppointmentDaoImpl {

    /** This method retrieves all appointments from database
     * @return allAppointments
     */
    public static ObservableList<Appointments> getAllAppointments()throws SQLException {

        ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){

            Appointments appointment = createAppointmentFromResultSet(rs);
            allAppointments.add(appointment);
        }
        return allAppointments;
    }


    /** This method retrieves all appointments by week
     * @return appointmentsByWeek
     */
    public static ObservableList<Appointments> getAllAppointmentsByWeek() throws SQLException {
        ObservableList<Appointments> appointmentsByWeek = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE WEEK(Start) = WEEK(CURDATE())";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Appointments appointment = createAppointmentFromResultSet(rs);
            appointmentsByWeek.add(appointment);
        }

        return appointmentsByWeek;
    }


    /** This method retrieves all appointments by month
     * @return appointmentsByMonth
     */
    public static ObservableList<Appointments> getAllAppointmentsByMonth() throws SQLException {
        ObservableList<Appointments> appointmentsByMonth = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE MONTH(Start) = MONTH(CURDATE())";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Appointments appointment = createAppointmentFromResultSet(rs);
            appointmentsByMonth.add(appointment);
        }

        return appointmentsByMonth;
    }

    /** This method retrieves all appointments by customer ID
     * @param customerID
     * @return appointmentsByCustomerID
     */
    public static ObservableList<Appointments> getAllAppointmentsByCustomerID(int customerID) throws SQLException {
        ObservableList<Appointments> appointmentsByCustomerID = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Appointments appointment = createAppointmentFromResultSet(rs);
            appointmentsByCustomerID.add(appointment);
        }

        return appointmentsByCustomerID;
    }

    /** This method retrieves all appointments by contact ID
     * @param contactID
     * @return appointmentsByContactID
     */
    public static ObservableList<Appointments> getAllAppointmentsByContactID(int contactID) throws SQLException {
        ObservableList<Appointments> appointmentsByContactID = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Contact_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, contactID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Appointments appointment = createAppointmentFromResultSet(rs);
            appointmentsByContactID.add(appointment);
        }

        return appointmentsByContactID;
    }

    /** This method adds a new appointment to the database
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param customerId
     * @param userId
     * @param contactId
     */
    public static void addAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int customerId, int userId, int contactId) throws SQLException {

        String sql = "INSERT INTO appointments "
                + "(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setTimestamp(7, createDate);
        ps.setString(8, createdBy);
        ps.setTimestamp(9, lastUpdate);
        ps.setString(10, lastUpdatedBy);
        ps.setInt(11, customerId);
        ps.setInt(12,userId);
        ps.setInt(13, contactId);
        ps.executeUpdate();
    }

    /** This method modifies an appointment from the database
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param customerId
     * @param userId
     * @param contactId
     */
    public static void modifyAppointment(int appointmentId, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int customerId, int userId, int contactId) throws SQLException {

        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setTimestamp(7, createDate);
        ps.setString(8, createdBy);
        ps.setTimestamp(9, lastUpdate);
        ps.setString(10, lastUpdatedBy);
        ps.setInt(11, customerId);
        ps.setInt(12,userId);
        ps.setInt(13, contactId);
        ps.setInt(14, appointmentId);
        ps.executeUpdate();
    }



    /** This method deletes an appointment from the database
     * @param appointmentId
     */
    public static void deleteAppointment(int appointmentId) throws SQLException{

        String sql = "DELETE FROM appointments WHERE APPOINTMENT_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
        ps.executeUpdate();
    }

    /** This method creates an 'Appointment' object from the 'ResultSet'
     * @param resultSet
     * @return Appointments
     */
    public static Appointments createAppointmentFromResultSet(ResultSet resultSet) throws SQLException {
        int appointmentID = resultSet.getInt("Appointment_ID");
        String title = resultSet.getString("Title");
        String description = resultSet.getString("Description");
        String location = resultSet.getString("Location");
        String appointmentType = resultSet.getString("Type");
        LocalDateTime start = resultSet.getTimestamp("Start").toLocalDateTime();
        LocalDateTime end = resultSet.getTimestamp("End").toLocalDateTime();
        LocalDateTime createDate = resultSet.getTimestamp("Create_Date").toLocalDateTime();
        String createdBy = resultSet.getString("Created_By");
        LocalDateTime lastUpdate = resultSet.getTimestamp("Last_Update").toLocalDateTime();
        String lastUpdatedBy = resultSet.getString("Last_Updated_By");
        int customerID = resultSet.getInt("Customer_ID");
        int userID = resultSet.getInt("User_ID");
        int contactID = resultSet.getInt("Contact_ID");

        return new Appointments(appointmentID, title, description, location, appointmentType, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID);
    }
}
