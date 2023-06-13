package controller;

import dao.AppointmentDaoImpl;
import dao.CustomerDaoImpl;
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
import model.Customers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML private TableColumn<Customers, Integer> CustomerIDCol;
    @FXML private TableColumn<Customers, String> addressCol;
    @FXML private TableView<Customers> customersTableView;
    @FXML private TableColumn<Customers, String> customerNameCol;
    @FXML private TableColumn<Customers, Integer> divisionIDCol;
    @FXML private TableColumn<Customers, String> phoneNumberCol;
    @FXML private TableColumn<Customers, String> postalCodeCol;

    /** This method initializes the customers view and populates the customers table
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ObservableList<Customers> customers = CustomerDaoImpl.getAllCustomers();
            CustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
            divisionIDCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
            customersTableView.setItems(customers);

        } catch (SQLException sqlException) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error retrieving appointments");
            alert.setContentText("There was an error retrieving the customers. Please try again later.");
            alert.showAndWait();
        }

    }

    /** This loads the 'AddCustomer.fxml' view
     * @param event
     */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This method deletes a customer
     * @param event
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws SQLException {

        Customers selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();

        if(selectedCustomer == null){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error Dialog");
            errorAlert.setContentText("Please select a customer to be deleted");
            errorAlert.showAndWait();
            return;
        } else{
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to proceed?");
            Optional<ButtonType> answer = confirmationAlert.showAndWait();
            if (answer.isPresent() && answer.get() == ButtonType.OK) {
                ObservableList<Appointments> appointments = AppointmentDaoImpl.getAllAppointmentsByCustomerID(selectedCustomer.getCustomerID());
                if (appointments.size() > 0) {
                    Alert confirmationAlert2 = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert2.getDialogPane().setPrefSize(500, 200);
                    confirmationAlert2.setTitle("Error Dialog");
                    confirmationAlert2.setContentText("Please delete all appointments for this customer first.\n \nAre you sure you want to delete all appointments for this customer?");
                    Optional<ButtonType> errorResponse = confirmationAlert2.showAndWait();
                    if (errorResponse.isPresent() && errorResponse.get() == ButtonType.OK) {
                        for (Appointments appointment : appointments) {
                            AppointmentDaoImpl.deleteAppointment(appointment.getAppointmentID());
                        }
                        CustomerDaoImpl.deleteCustomer(selectedCustomer.getCustomerID());
                    }
                }else {
                    CustomerDaoImpl.deleteCustomer(selectedCustomer.getCustomerID());
                }
            }
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Customer deleted successfully. ");
        alert.showAndWait();
        ObservableList<Customers> allCustomers = CustomerDaoImpl.getAllCustomers();
        customersTableView.setItems(allCustomers);
        customersTableView.refresh();
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

    /** This method loads the 'ModifyCustomer.fxml' view
     * @param event
     */
    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException {

        Customers selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();

        if(selectedCustomer == null){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error Dialog");
            errorAlert.setContentText("Please select a customer to be edited");
            errorAlert.showAndWait();
            return;
        }

        ModifyCustomerController.receiveDetails(selectedCustomer);
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }
}
