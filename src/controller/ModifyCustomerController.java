package controller;

import dao.CountriesDaoImpl;
import dao.CustomerDaoImpl;
import dao.FirstLevelDivisionsDaoImpl;
import helper.loginUserName;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Countries;
import model.Customers;
import model.FirstLevelDivisions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable {

    Stage stage;
    Parent scene;

    private static Customers selectedCustomer;
    private static int selectedDivisionID;

    @FXML private TextField addressTxt;
    @FXML private ComboBox<Countries> countryCBox;
    @FXML private TextField customerIDTxt;
    @FXML private TextField customerNameTxt;
    @FXML private ComboBox<FirstLevelDivisions> firstLevelDivisionCBox;
    @FXML private TextField phoneNumberTxt;
    @FXML private TextField postalCodeTxt;

    /** This method initializes the modifyCustomer view
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {

            selectedDivisionID = selectedCustomer.getDivisionID();

            ObservableList<Countries> countries = CountriesDaoImpl.getAllCountries();
            countryCBox.setItems(countries);

            int countryID = FirstLevelDivisionsDaoImpl.getCountryIDByDivisionID(selectedDivisionID);
            Countries selectedCountry = CountriesDaoImpl.getCountryByID(countryID);
            countryCBox.getSelectionModel().select(selectedCountry);
            if (selectedCountry.getCountryID() == 1) {
                firstLevelDivisionCBox.setItems(FirstLevelDivisionsDaoImpl.getAllUSDivisions());
            }
            else if (selectedCountry.getCountryID() == 2) {
                firstLevelDivisionCBox.setItems(FirstLevelDivisionsDaoImpl.getAllUKDivisions());
            }
            else if (selectedCountry.getCountryID() == 3) {
                firstLevelDivisionCBox.setItems(FirstLevelDivisionsDaoImpl.getAllCanadaDivisions());
            }

            FirstLevelDivisions selectedDivision = FirstLevelDivisionsDaoImpl.getDivisionByDivisionID(selectedDivisionID);
            firstLevelDivisionCBox.getSelectionModel().select(selectedDivision);

            if(selectedCustomer != null) {
                customerIDTxt.setText(String.valueOf(selectedCustomer.getCustomerID()));
                customerNameTxt.setText(selectedCustomer.getCustomerName());
                addressTxt.setText(selectedCustomer.getAddress());
                postalCodeTxt.setText(selectedCustomer.getPostalCode());
                phoneNumberTxt.setText(selectedCustomer.getPhone());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** This method cancels the action of modifying the customer information and loads the 'Customer.fxml' view
     * @param event
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Customer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This method saves the changes to the customer information and loads the 'Customer.fxml' view
     * @param event
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {

        try{

            int customerID  = Integer.parseInt(customerIDTxt.getText());
            String name = customerNameTxt.getText();
            String address = addressTxt.getText();
            String postalCode = postalCodeTxt.getText();
            String phone = phoneNumberTxt.getText();
            Timestamp createDate = Timestamp.valueOf(LocalDateTime.now());
            String createdBy = loginUserName.currentUserName;
            Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());
            String lastUpdateBy = loginUserName.currentUserName;
            FirstLevelDivisions selectedDivision = firstLevelDivisionCBox.getSelectionModel().getSelectedItem();
            int divisionID = selectedDivision.getDivisionID();

            if(name.isEmpty() || address.isEmpty() || postalCode.isEmpty() || phone.isEmpty() || countryCBox.getSelectionModel().getSelectedItem() == null || firstLevelDivisionCBox.getSelectionModel().getSelectedItem() == null){

                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error Dialog");
                errorAlert.setContentText("Please check for empty or incorrect input for all the fields");
                errorAlert.showAndWait();

            }else{

                CustomerDaoImpl.modifyCustomer(customerID, name, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy, divisionID);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Customer updated successfully.");
                alert.showAndWait();

                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/Customer.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }

        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to update customer.");
            e.printStackTrace();
            alert.showAndWait();
        }
    }

    /** This method loads the first level division list according to selected country
     * @param event
     */
    @FXML
    void onActionCountryCBox(ActionEvent event) throws SQLException {

        try{

            if (countryCBox.getSelectionModel().getSelectedIndex() == 0) {
                firstLevelDivisionCBox.setItems(FirstLevelDivisionsDaoImpl.getAllUSDivisions());
            }
            else if (countryCBox.getSelectionModel().getSelectedIndex() == 1) {
                firstLevelDivisionCBox.setItems(FirstLevelDivisionsDaoImpl.getAllUKDivisions());
            }
            else if (countryCBox.getSelectionModel().getSelectedIndex() == 2) {
                firstLevelDivisionCBox.setItems(FirstLevelDivisionsDaoImpl.getAllCanadaDivisions());
            }
            firstLevelDivisionCBox.setValue(null);
        }catch (NullPointerException | SQLException e){
            e.printStackTrace();
        }

    }

    @FXML
    void onActionFirstLevelDivisionCBox(ActionEvent event) throws SQLException {

    }


    public static void receiveDetails(Customers customer){
        if(customer != null) {
            selectedCustomer = customer;
        }
    }
}
