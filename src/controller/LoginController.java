package controller;

import dao.AppointmentDaoImpl;
import dao.UsersDaoImpl;
import helper.UserActivityTracker;
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
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML private Label loginLbl;
    @FXML private Button loginBtn;
    @FXML private Label passwordLbl;
    @FXML private TextField passwordTxt;
    @FXML private Label timeZoneAnswerLbl;
    @FXML private Label timeZoneLbl;
    @FXML private Label userNameLbl;
    @FXML private TextField userNameTxt;

    ResourceBundle rb = ResourceBundle.getBundle("language", Locale.getDefault());

    /** This method initializes the login view
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        timeZoneAnswerLbl.setText(String.valueOf(ZoneId.systemDefault()));
        loginLbl.setText(rb.getString("login"));
        userNameLbl.setText(rb.getString("username"));
        passwordLbl.setText(rb.getString("password"));
        timeZoneLbl.setText(rb.getString("timezone"));
        loginBtn.setText(rb.getString("login"));

    }

    /** This method verifies user's credentials and logins into main menu
     * @param event
     */
    @FXML
    void onActionLogin(ActionEvent event) throws IOException, SQLException {


        if (userNameTxt.getText().equals("") || passwordTxt.getText().equals("")) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, rb.getString("error"));
            errorAlert.setTitle(rb.getString("errorDialog"));
            errorAlert.setContentText(rb.getString("pleaseCheckForEmptyFields"));
            errorAlert.showAndWait();
        } else {
            Users loggedInUser = UsersDaoImpl.checkUserCredentials(userNameTxt.getText(), passwordTxt.getText());

            if (loggedInUser != null) {

                checkUpcomingAppointments();

                loginUserName.currentUserName = loggedInUser.getUserName();
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

                UserActivityTracker.logInReport(userNameTxt.getText(), " successfully logged in at ");

            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, rb.getString("error"));
                errorAlert.setTitle(rb.getString("errorDialog"));
                errorAlert.setContentText(rb.getString("pleaseCheckForIncorrectUsernameAndPassword"));
                errorAlert.showAndWait();

                UserActivityTracker.logInReport(userNameTxt.getText(), " gave invalid log-in at ");
            }
        }
    }

    /** This method checks for upcoming appointments within 15 minutes
     * Lambda #2 - In this lambda expression, forEach is used to iterate over each element in the allAppointments, and the add method is called on allAppointmentsWithin15 to add each appointment to the list.
     * @throws SQLException
     */
    public void checkUpcomingAppointments() throws SQLException {
        ObservableList<Appointments> allAppointments = AppointmentDaoImpl.getAllAppointments();
        ObservableList<Appointments> allAppointmentsWithin15 = FXCollections.observableArrayList();
        LocalDateTime timeNow = LocalDateTime.now();
        LocalDateTime timeNowPlus15 = timeNow.plusMinutes(15);

        if(allAppointments != null){
            StringBuilder upcomingAppointments = new StringBuilder();
            //Replaced with lambda
            /*for (Appointments appointment : allAppointments) {
                if ((appointment.getStart().isAfter(timeNow) || appointment.getStart().isEqual(timeNow)) && (appointment.getStart().isBefore(timeNowPlus15) || appointment.getStart().isEqual(timeNowPlus15))) {
                    allAppointmentsWithin15.add(appointment);
                    upcomingAppointments.append("Appointment ID: ")
                            .append(appointment.getAppointmentID())
                            .append(" Starting at: ")
                            .append(appointment.getStart())
                            .append("\n");
                }
            }*/
            allAppointments.forEach(appointment -> {
                if ((appointment.getStart().isAfter(timeNow) || appointment.getStart().isEqual(timeNow)) && (appointment.getStart().isBefore(timeNowPlus15) || appointment.getStart().isEqual(timeNowPlus15))) {
                    allAppointmentsWithin15.add(appointment);
                    upcomingAppointments.append("Appointment ID: ")
                            .append(appointment.getAppointmentID())
                            .append(" Starting at: ")
                            .append(appointment.getStart())
                            .append("\n");
                }
            });
            if (allAppointmentsWithin15.size() > 0) {
                Alert warningAlert = new Alert(Alert.AlertType.WARNING);
                warningAlert.setResizable(true);
                warningAlert.getDialogPane().setPrefSize(500, 200);
                warningAlert.setTitle("Upcoming Appointments");
                warningAlert.setHeaderText("The following appointments are starting within the next 15 minutes: ");
                warningAlert.setContentText(upcomingAppointments.toString());
                warningAlert.showAndWait();
            }
            else {
                Alert errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
                errorAlert.setTitle("No Upcoming Appointments");
                errorAlert.setContentText("There are no appointments starting within 15 minutes");
                errorAlert.showAndWait();
            }
        }
    }
}
