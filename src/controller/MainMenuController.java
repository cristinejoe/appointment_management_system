package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;
    @FXML private Button appointmentsBtn;
    @FXML private Button customersBtn;
    @FXML private Button logOutBtn;
    @FXML private Button reportsBtn;


    /** This method initializes the Main Menu view
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /** This method loads the 'Appointments.fxml' view
     * @param event
     */
    @FXML
    void onActionAppointments(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        scene.requestFocus();
    }

    /** This method loads the 'Customers.fxml' view
     * @param event
     */
    @FXML
    void onActionCustomers(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Customer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        scene.requestFocus();
    }

    /** This method loads the 'Report.fxml' view
     * @param event
     */
    @FXML
    void onActionReports(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Report.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        scene.requestFocus();
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

}
