package controller;

import dao.AppointmentDaoImpl;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML private TableColumn<Appointments, Integer> contactCol;
    @FXML private TableColumn<Appointments, Integer> appointmentIDCol;
    @FXML private TableView<Appointments> appointmentsTableView;
    @FXML private TableColumn<Appointments, Integer> customerIDCol;
    @FXML private TableColumn<Appointments, String> descriptionCol;
    @FXML private TableColumn<Appointments, Calendar> endDateTimeCol;
    @FXML private TableColumn<Appointments, String> locationCol;
    @FXML private TableColumn<Appointments, Calendar> startDateTimeCol;
    @FXML private TableColumn<Appointments, String> titleCol;
    @FXML private TableColumn<Appointments, String> typeCol;
    @FXML private TableColumn<Appointments, Integer> userIDCol;
    @FXML private ToggleGroup tGroup;


    /** This method initializes the Appointments view and populates the appointments table
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ObservableList<Appointments> appointments = AppointmentDaoImpl.getAllAppointments();
            appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("AppointmentID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
            locationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
            contactCol.setCellValueFactory(new PropertyValueFactory<>("ContactID"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
            startDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("Start"));
            endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("End"));
            customerIDCol.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
            userIDCol.setCellValueFactory(new PropertyValueFactory<>("UserID"));

            appointmentsTableView.getItems().clear();
            appointmentsTableView.setItems(appointments);

        } catch (SQLException sqlException) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error retrieving appointments");
            alert.setContentText("There was an error retrieving the appointments. Please try again later.");
            alert.showAndWait();
        }
    }

    /** This method loads the 'AddAppointment.fxml' view
     * @param event
     */
    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method deletes an appointment
     * @param event
     */
    @FXML
    void onActionDeleteAppointment(ActionEvent event) throws SQLException {

        if (appointmentsTableView.getSelectionModel().isEmpty()){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Please select an appointment to be deleted");
            errorAlert.showAndWait();
            return;
        }else{
            int appointmentID = appointmentsTableView.getSelectionModel().getSelectedItem().getAppointmentID();
            String appointmentType = appointmentsTableView.getSelectionModel().getSelectedItem().getType();
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setContentText("Are you sure you want to delete this appointment?");
            Optional<ButtonType> answer = confirmationAlert.showAndWait();

            if (answer.isPresent() && answer.get() == ButtonType.OK) {
                AppointmentDaoImpl.deleteAppointment(appointmentID);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Appointment ID: " + appointmentID + ", of type " + appointmentType + " has been deleted successfully");
                alert.showAndWait();;
            }

            ObservableList<Appointments> allAppointments = AppointmentDaoImpl.getAllAppointments();
            appointmentsTableView.setItems(allAppointments);
            appointmentsTableView.refresh();
        }
    }

    /** This method logs user out and loads the 'Login.fxml' view
     * @param event
     */
    @FXML
    void onActionLogOut(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method loads the 'MainMenu.fxml' view
     * @param event
     */
    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method loads the 'ModifyAppointment.fxml' view
     * @param event
     */
    @FXML
    void onActionModifyAppointment(ActionEvent event) throws IOException {

        Appointments selectedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();

        if(selectedAppointment == null){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error Dialog");
            errorAlert.setContentText("Please select an appointment to be edited");
            errorAlert.showAndWait();
            return;
        }
        ModifyAppointmentController.receiveDetails(selectedAppointment);
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This method loads all appointments
     * @param event
     */
    @FXML
    void onActionViewAll(ActionEvent event) throws SQLException {

        ObservableList<Appointments> allAppointments = AppointmentDaoImpl.getAllAppointments();
        appointmentsTableView.setItems(allAppointments);
    }

    /** This method loads all appointments by month
     * @param event
     */
    @FXML
    void onActionViewByMonth(ActionEvent event) throws SQLException {

        ObservableList<Appointments> allAppointmentsByMonth = AppointmentDaoImpl.getAllAppointmentsByMonth();
        appointmentsTableView.setItems(allAppointmentsByMonth);

    }

    /** This method loads all appointments by week
     * @param event
     */
    @FXML
    void onActionViewByWeek(ActionEvent event) throws SQLException {

        ObservableList<Appointments> allAppointmentsByWeek = AppointmentDaoImpl.getAllAppointmentsByWeek();
        appointmentsTableView.setItems(allAppointmentsByWeek);

    }
}
