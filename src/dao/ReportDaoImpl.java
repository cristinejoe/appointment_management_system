package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/** This class was created to generate all the reports. */
public class ReportDaoImpl {


    /** This method selects the total number of customer appointments by type and month
     * @param month
     * @param type
     * @return appointmentsMonthType
     */
    public static ObservableList<Appointments> getAllAppointmentsByMonthAndType(String month, String type) throws SQLException {

        ObservableList<Appointments> appointmentsMonthType = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Appointments WHERE MONTHNAME(Start) = ? AND Type = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, month);
        ps.setString(2, type);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

            Appointments appointment = createAppointmentFromResultSet(rs);
            appointmentsMonthType.add(appointment);
        }
        return appointmentsMonthType;
    }

    /** This method retrieves all appointments by month
     * @param month
     * @return appointmentsByMonth
     */
    public static ObservableList<Appointments> getAllAppointmentsByMonth(String month) throws SQLException {
        ObservableList<Appointments> appointmentsByMonth = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE MONTHNAME(Start) = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, month);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Appointments appointment = createAppointmentFromResultSet(rs);
            appointmentsByMonth.add(appointment);
        }

        return appointmentsByMonth;
    }

    /** This method retrieves all appointments by type
     * @param type
     * @return appointmentsByType
     */
    public static ObservableList<Appointments> getAllAppointmentsByType(String type) throws SQLException {
        ObservableList<Appointments> appointmentsByType = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Type = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, type);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Appointments appointment = createAppointmentFromResultSet(rs);
            appointmentsByType.add(appointment);
        }

        return appointmentsByType;
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
