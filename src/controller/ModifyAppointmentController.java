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

public class ModifyAppointmentController implements Initializable {

    Stage stage;
    Parent scene;
    private static Appointments selectedAppointment;

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

    /** This method initializes the ModifyAppointment view
     * Lambda #1 - In this lambda expression, forEach is used to iterate over each element in the contactsList, and the add method is called on contactList to add each contact's name to the list.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {

            userIDCBox.setValue(UsersDaoImpl.getUserByUserID(selectedAppointment.getUserID()));
            userIDCBox.setItems(UsersDaoImpl.getUsers());

            customerIDCBox.setValue(CustomerDaoImpl.getCustomerByCustomerID(selectedAppointment.getCustomerID()));
            customerIDCBox.setItems(CustomerDaoImpl.getAllCustomers());

            ObservableList<String> contactList = FXCollections.observableArrayList();
            ObservableList<Contacts> contactsList = ContactDaoImpl.getAllContacts();
            contactCBox.setValue(ContactDaoImpl.getContactByContactID(selectedAppointment.getContactID()));
            contactCBox.setItems(contactsList);

            contactsList.forEach(contact -> contactList.add(contact.getContactName()));

            //Replaced by lambda above
            /*for (Contacts contact : contactsList) {
                contactList.add(contact.getContactName());
            }*/

            startTimeCBox.setItems(getAppointmentTimes());
            endTimeCBox.setItems(getAppointmentTimes());

            appointmentIDTxt.setText(String.valueOf(selectedAppointment.getAppointmentID()));
            titleTxt.setText(selectedAppointment.getTitle());
            descriptionTxt.setText(selectedAppointment.getDescription());
            locationTxt.setText(selectedAppointment.getLocation());
            typeTxt.setText(selectedAppointment.getType());
            startDateDP.setValue(selectedAppointment.getStart().toLocalDate());
            endDateDP.setValue(selectedAppointment.getEnd().toLocalDate());
            startTimeCBox.setValue(selectedAppointment.getStart().toLocalTime());
            endTimeCBox.setValue(selectedAppointment.getEnd().toLocalTime());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /** This method cancels the action of modifying the appointment and loads the 'Appointment.fxml' view
     * @param event
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method saves the changes to the appointment and loads the 'Appointment.fxml' view
     * @param event
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException, SQLException {

        try{

            int appointmentID = Integer.parseInt(appointmentIDTxt.getText());
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
            Integer customerID = customerIDCBox.getValue().getCustomerID();
            Integer userID = userIDCBox.getValue().getUserID();
            Integer contactID = contactCBox.getValue().getContactID();

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
            if(title.isEmpty() || description.isEmpty() || location.isEmpty() || type.isEmpty() || customerID == null || userID == null || contactID == null){

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
                errorAlert.setContentText("Please check if start and end time are not be the same or if start time is set be before end time");
                errorAlert.showAndWait();
            }

            //checking if start time and end time are within business hours
            else if (ESTStartToLDT.isBefore(startTimeOfficeHours) || ESTEndToLDT.isAfter(endTimeOfficeHours)) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.getDialogPane().setPrefSize(550, 150);
                errorAlert.setTitle("Error Dialog");
                errorAlert.setContentText("Appointment can not be outside business hours, which is Monday through Friday (8am-22pm EST)");
                errorAlert.showAndWait();

            } else if(AddAppointmentController.checkOverlappingAppointments(customerID, appointmentID, appointmentStart, appointmentEnd)){
                return;

            } else {

                AppointmentDaoImpl.modifyAppointment(appointmentID, title, description, location, type, appointmentStart, appointmentEnd, createDate, createdBy, lastUpdate, lastUpdateBy, customerID, userID, contactID);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/Appointment.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Appointment updated successfully.");
                alert.showAndWait();
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to update appointment.");
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


    public static void receiveDetails(Appointments appointment){
        if(appointment != null) {
            selectedAppointment = appointment;
        }
    }
}
