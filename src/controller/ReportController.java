package controller;


import dao.AppointmentDaoImpl;
import dao.ContactDaoImpl;
import dao.CustomerDaoImpl;
import dao.ReportDaoImpl;
import javafx.collections.FXCollections;
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
import model.Contacts;
import model.Customers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

    Stage stage;
    Parent scene;
    private ObservableList<String> months;
    @FXML private TableColumn<Appointments, Integer> contactCol;
    @FXML private TableColumn<Appointments, Integer> appointmentIDCol;
    @FXML private TableView<Appointments> appointmentsTableView;
    @FXML private ComboBox<Customers> customerIDCBox;
    @FXML private ComboBox<Contacts> contactCBox;
    @FXML private TableColumn<Appointments, Integer> customerIDCol;
    @FXML private TableColumn<Appointments, String> descriptionCol;
    @FXML private TableColumn<Appointments, Calendar> endDateTimeCol;
    @FXML private TableColumn<Appointments, String> locationCol;
    @FXML private ComboBox<String> monthCBox;
    @FXML private TableColumn<Appointments, Calendar> startDateTimeCol;
    @FXML private TableColumn<Appointments, Integer> titleCol;
    @FXML private ComboBox<Appointments> typeCBox;
    @FXML private TableColumn<Appointments, String> typeCol;
    @FXML private TableColumn<Appointments, Integer> userIDCol;
    @FXML private Label schedulesByContactLbl;
    @FXML private Label schedulesByCustomertLbl;
    @FXML private Label totalAppointmentsbyMonthandTypeLbl;
    @FXML private Button getTotalBtn;


    /** This method initializes the reports view
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
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

            months = FXCollections.observableArrayList("Select a month","January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
            ObservableList<String> allAppointmentsList = FXCollections.observableArrayList();
            ObservableList<Appointments> allAppointmentsTypes = AppointmentDaoImpl.getAllAppointments();
            allAppointmentsTypes.forEach(type -> allAppointmentsList.add(type.getType()));

            typeCBox.setItems(allAppointmentsTypes);
            monthCBox.setItems(months);
            contactCBox.setItems(ContactDaoImpl.getAllContacts());
            customerIDCBox.setItems(CustomerDaoImpl.getAllCustomers());

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }


    /** This method loads all appointments by months in the tableview
     * @param event
     */
    @FXML
    void onActionMonth(ActionEvent event) throws SQLException {

        try {
            String selectedMonth = monthCBox.getSelectionModel().getSelectedItem();
            if(selectedMonth == null) {
                selectedMonth = monthCBox.getSelectionModel().getSelectedItem();
            }else{
                ObservableList<Appointments> appointments = ReportDaoImpl.getAllAppointmentsByMonth(selectedMonth);
                appointmentsTableView.setItems(appointments);
                totalAppointmentsbyMonthandTypeLbl.setText("Total Number of Appointments by Month and Type");
                schedulesByCustomertLbl.setText("Schedules By Customer");
                schedulesByContactLbl.setText("Schedules By Contact");
                contactCBox.setValue(null);
                customerIDCBox.setValue(null);
            }

        }catch (NullPointerException | SQLException e){
            e.printStackTrace();
        }

    }

    /** This method loads all appointments by type in the tableview
     * @param event
     */
    @FXML
    void onActionType(ActionEvent event) throws SQLException {

        try {
            Appointments selectedType = typeCBox.getSelectionModel().getSelectedItem();
            if(selectedType == null){
                typeCBox.getSelectionModel().getSelectedItem();
            }else{
                ObservableList<Appointments> appointments = ReportDaoImpl.getAllAppointmentsByType(String.valueOf(selectedType));
                appointmentsTableView.setItems(appointments);
                totalAppointmentsbyMonthandTypeLbl.setText("Total Number of Appointments by Month and Type");
                schedulesByCustomertLbl.setText("Schedules By Customer");
                schedulesByContactLbl.setText("Schedules By Contact");
                contactCBox.setValue(null);
                customerIDCBox.setValue(null);
            }
        }catch (NullPointerException | SQLException e){
            e.printStackTrace();
        }
    }

    /** This method calculates the total number of appointments by month and type
     * @param event
     */
    @FXML
    void onActionTotal(ActionEvent event) throws SQLException {

        try {
            Appointments selectedType = typeCBox.getSelectionModel().getSelectedItem();
            String selectedMonth = monthCBox.getSelectionModel().getSelectedItem();
            if(selectedType == null && selectedMonth == null) {
                typeCBox.getSelectionModel().getSelectedItem();
                monthCBox.getSelectionModel().getSelectedItem();
            }else{
                ObservableList<Appointments> appointments = ReportDaoImpl.getAllAppointmentsByMonthAndType(selectedMonth, String.valueOf(selectedType));
                appointmentsTableView.setItems(appointments);
                totalAppointmentsbyMonthandTypeLbl.setText("Total Number of Appointments by Month and Type: " + appointments.size());
                schedulesByCustomertLbl.setText("Schedules By Customer");
                schedulesByContactLbl.setText("Schedules By Contact");
                contactCBox.setValue(null);
                customerIDCBox.setValue(null);
            }
        }catch (NullPointerException | SQLException e){
            e.printStackTrace();
        }
    }

    /** This method loads all appointments by customers ID in the tableview
     * @param event
     */
    @FXML
    void onActionCustomerID(ActionEvent event) {

        try {
            Customers selectedCustomer = customerIDCBox.getValue();
            if(selectedCustomer == null) {
                selectedCustomer = customerIDCBox.getValue();
            }else{
                ObservableList<Appointments> appointments = AppointmentDaoImpl.getAllAppointmentsByCustomerID(selectedCustomer.getCustomerID());
                appointmentsTableView.setItems(appointments);
                schedulesByCustomertLbl.setText("Schedules By Customer: " + appointments.size());
                totalAppointmentsbyMonthandTypeLbl.setText("Total Number of Appointments by Month and Type");
                schedulesByContactLbl.setText("Schedules By Contact");
                contactCBox.setValue(null);
                monthCBox.setValue(null);
                typeCBox.setValue(null);
            }
        }catch (NullPointerException | SQLException e){
            e.printStackTrace();
        }
    }

    /** This method loads all appointments by contact ID in the tableview
     * @param event
     */
    @FXML
    void onActionContact(ActionEvent event) {

        try {
            Contacts selectedContact = contactCBox.getValue();
            if(selectedContact == null) {
                selectedContact = contactCBox.getValue();
            }else{
                ObservableList<Appointments> appointments = AppointmentDaoImpl.getAllAppointmentsByContactID(selectedContact.getContactID());
                appointmentsTableView.setItems(appointments);
                schedulesByContactLbl.setText("Schedules By Contact: " + appointments.size());
                schedulesByCustomertLbl.setText("Schedules By Customer");
                totalAppointmentsbyMonthandTypeLbl.setText("Total Number of Appointments by Month and Type");
                customerIDCBox.setValue(null);
                monthCBox.setValue(null);
                typeCBox.setValue(null);
            }
        }catch (NullPointerException | SQLException e){
            e.printStackTrace();
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

}
