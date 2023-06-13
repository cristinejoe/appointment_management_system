package controller;

import dao.AppointmentDaoImpl;
import dao.ContactDaoImpl;
import dao.CustomerDaoImpl;
import dao.UsersDaoImpl;
import helper.loginUserName;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.Users;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {


    Stage stage;
    Parent scene;

    @FXML private TextField appointmentIDTxt;
    @FXML private ComboBox<Contacts> contactCBox;
    @FXML private ComboBox<Customers> customerIDCBox;
    @FXML private TextField descriptionTxt;
    @FXML private DatePicker endDateDP;
    @FXML private ComboBox<LocalTime> endTimeCBox;
    @FXML private TextField locationTxt;
    @FXML private DatePicker startDateDP;
    @FXML private ComboBox<LocalTime> startTimeCBox;
    @FXML private TextField titleTxt;
    @FXML private TextField typeTxt;
    @FXML private ComboBox<Users> userIDCBox;


    /** This method initializes the addAppointment view
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            customerIDCBox.setItems(CustomerDaoImpl.getAllCustomers());
            userIDCBox.setItems(UsersDaoImpl.getUsers());
            contactCBox.setItems(ContactDaoImpl.getAllContacts());

            startTimeCBox.setItems(getAppointmentTimes());
            endTimeCBox.setItems(getAppointmentTimes());

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    /** This method cancels the action of adding a new appointment and loads the 'Appointment.fxml' view
     * @param event
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method saves the new appointment and loads the 'Appointment.fxml' view
     * @param event
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException, SQLException {

        try{
            String title = titleTxt.getText();
            String description = descriptionTxt.getText();
            String location = locationTxt.getText();
            String type = typeTxt.getText();
            LocalDateTime appointmentStart = LocalDateTime.of(startDateDP.getValue(), startTimeCBox.getSelectionModel().getSelectedItem());
            LocalDateTime appointmentEnd = LocalDateTime.of(endDateDP.getValue(), endTimeCBox.getSelectionModel().getSelectedItem());
            Timestamp createDate = Timestamp.valueOf(LocalDateTime.now());
            String createdBy = loginUserName.currentUserName;
            Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());
            String lastUpdateBy = loginUserName.currentUserName;
            Contacts selectedContact = contactCBox.getSelectionModel().getSelectedItem();
            int contactID = selectedContact != null ? selectedContact.getContactID() : 0;

            Customers selectedCustomer = customerIDCBox.getSelectionModel().getSelectedItem();
            int customerID = selectedCustomer != null ? selectedCustomer.getCustomerID() : 0;

            Users selectedUser = userIDCBox.getSelectionModel().getSelectedItem();
            int userID = selectedUser != null ? selectedUser.getUserID() : 0;

            // Getting appointment start date and time chosen by user in system default
            ZonedDateTime appointmentStartToSystemDefault = appointmentStart.atZone(ZoneId.systemDefault());
            ZonedDateTime appointmentEndToSystemDefault = appointmentEnd.atZone(ZoneId.systemDefault());

            //Converting appointment start date and time into Eastern Time
            ZonedDateTime appointmentStartEasternTime = appointmentStartToSystemDefault.withZoneSameInstant(ZoneId.of("America/New_York"));
            ZonedDateTime appointmentEndEasternTime = appointmentEndToSystemDefault.withZoneSameInstant(ZoneId.of("America/New_York"));

            //Getting Eastern Time in LocalDateTime
            LocalDateTime ESTStartToLDT = appointmentStartEasternTime.toLocalDateTime();
            LocalDateTime ESTEndToLDT = appointmentEndEasternTime.toLocalDateTime();

            //Getting office hours in LocalDateTime
            LocalDateTime startTimeOfficeHours = LocalDateTime.of(startDateDP.getValue(), LocalTime.of(8, 0));
            LocalDateTime endTimeOfficeHours = LocalDateTime.of(endDateDP.getValue(), LocalTime.of(22, 0));


            //checking if fields are empty
            if(title.isEmpty() || description.isEmpty() || location.isEmpty() || type.isEmpty() || selectedCustomer == null || selectedUser == null || selectedContact == null){

                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error Dialog");
                errorAlert.setContentText("Please check for empty or incorrect input for all the fields");
                errorAlert.showAndWait();
            }

            //checking if start date and end date fields are empty
            else if (startDateDP.getValue() == null || startDateDP.getValue() == null) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error Dialog");
                errorAlert.setContentText("Please check for empty start and or end date fields");
                errorAlert.showAndWait();
            }

            //checking if start date is after end date
            else if (startDateDP.getValue().isAfter(endDateDP.getValue())) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error Dialog");
                errorAlert.setContentText("Start date should be before end date");
                errorAlert.showAndWait();
            }

            //checking if start time and end time fields are empty
            else if (startTimeCBox.getSelectionModel().getSelectedItem() == null || endTimeCBox.getSelectionModel().getSelectedItem() == null) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error Dialog");
                errorAlert.setContentText("Start and end appointment time should not be empty");
                errorAlert.showAndWait();
            }

            //checking if start time and end time are the same and if start time is after end time
            else if (appointmentStart.equals(appointmentEnd) || appointmentStart.isAfter(appointmentEnd)) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.getDialogPane().setPrefSize(550, 150);
                errorAlert.setTitle("Error Dialog");
                errorAlert.setContentText("Please check if start and end time are not the same or if start time is set be before end time");
                errorAlert.showAndWait();
            }

            //checking if start time and end time are within business hours
            else if (ESTStartToLDT.isBefore(startTimeOfficeHours) || ESTEndToLDT.isAfter(endTimeOfficeHours)) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.getDialogPane().setPrefSize(550, 150);
                errorAlert.setTitle("Error Dialog");
                errorAlert.setContentText("Appointment can not be outside business hours, which is Monday through Friday (8am-22pm EST)");
                errorAlert.showAndWait();

            } else if(checkOverlappingAppointments(customerID, 0, appointmentStart, appointmentEnd)){
                return;

            } else{

                AppointmentDaoImpl.addAppointment(title, description, location, type, appointmentStart, appointmentEnd, createDate, createdBy, lastUpdate, lastUpdateBy, customerID, userID, contactID);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Appointment created successfully.");
                alert.showAndWait();

                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/Appointment.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }

        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to create appointment.");
            e.printStackTrace();
            alert.showAndWait();
        }
    }

    /** This method loads the combo boxes with start and end times
     * @return appointmentTimes
     */
    private ObservableList<LocalTime> getAppointmentTimes() {
        ObservableList<LocalTime> appointmentTimes = FXCollections.observableArrayList();
        for (int hour = 0; hour < 24; hour++) {
            for (int minute = 0; minute < 60; minute += 30) {
                appointmentTimes.add(LocalTime.of(hour, minute));
            }
        }
        return appointmentTimes;
    }

    /** This method checks for overlapping appointments start and end times
     * @param customerID
     * @param appointmentID
     * @param appointmentStart
     * @param appointmentEnd
     * @return true or false depending whether there is an overlapping
     */
    public static boolean checkOverlappingAppointments(int customerID, int appointmentID, LocalDateTime appointmentStart, LocalDateTime appointmentEnd) throws Exception {
        ObservableList<Appointments> Allappointments = AppointmentDaoImpl.getAllAppointments();

        for(Appointments appointments : Allappointments){
            LocalDateTime checkAppointmentStart = appointments.getStart();
            LocalDateTime checkAppointmentEnd = appointments.getEnd();

            if (customerID != appointments.getCustomerID()){
                continue;

            }
            if(appointmentID == appointments.getAppointmentID()){
                continue;

            }
            else if(checkAppointmentStart.isEqual(appointmentStart) || checkAppointmentStart.isEqual(appointmentEnd) || checkAppointmentEnd.isEqual(appointmentStart) || checkAppointmentEnd.isEqual(appointmentEnd)){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.getDialogPane().setPrefSize(500, 150);
                errorAlert.setTitle("Error Dialog");
                errorAlert.setContentText("There can't be overlapping start or end times with existing appointments");
                errorAlert.showAndWait();
                return true;
            }else if(appointmentStart.isAfter(checkAppointmentStart) && appointmentStart.isBefore(checkAppointmentEnd)){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.getDialogPane().setPrefSize(500, 150);
                errorAlert.setTitle("Error Dialog");
                errorAlert.setContentText("There can't be overlapping start appointment time with existing ongoing appointment");
                errorAlert.showAndWait();
                return true;
            }else if(appointmentEnd.isAfter(checkAppointmentStart) && appointmentEnd.isBefore(checkAppointmentEnd)){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.getDialogPane().setPrefSize(500, 150);
                errorAlert.setTitle("Error Dialog");
                errorAlert.setContentText("There can't be overlapping end appointment time with existing ongoing appointment");
                errorAlert.showAndWait();
                return true;
            }else if(appointmentEnd.isAfter(checkAppointmentEnd) && appointmentStart.isBefore(checkAppointmentStart)){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.getDialogPane().setPrefSize(500, 150);
                errorAlert.setTitle("Error Dialog");
                errorAlert.setContentText("There can't be overlapping end appointment time with existing ongoing appointment");
                errorAlert.showAndWait();
                return true;
            }
        }
        return false;
    }

}
