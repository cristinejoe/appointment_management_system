package controller;


import dao.CountriesDaoImpl;
import dao.CustomerDaoImpl;
import dao.FirstLevelDivisionsDaoImpl;
import helper.loginUserName;
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
import model.FirstLevelDivisions;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML private TextField addressTxt;
    @FXML private ComboBox<Countries> countryCBox;
    @FXML private TextField customerIDTxt;
    @FXML private TextField customerNameTxt;
    @FXML private ComboBox<FirstLevelDivisions> firstLevelDivisionCBox;
    @FXML private TextField phoneNumberTxt;
    @FXML private TextField postalCodeTxt;

    /** This method initializes the addCustomer view
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            countryCBox.setItems(CountriesDaoImpl.getAllCountries());
            firstLevelDivisionCBox.setDisable(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** This method cancels the action of adding a new customer and loads the 'Customer.fxml' view
     * @param event
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Customer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This method saves the new customer information and loads the 'Customer.fxml' view
     * @param event
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {

        try{
            String customerName = customerNameTxt.getText();
            String address = addressTxt.getText();
            String postalCode = postalCodeTxt.getText();
            String phoneNumber = phoneNumberTxt.getText();
            Timestamp createDate = Timestamp.valueOf(LocalDateTime.now());
            String createdBy = loginUserName.currentUserName;
            Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());
            String lastUpdateBy = loginUserName.currentUserName;
            FirstLevelDivisions division = firstLevelDivisionCBox.getValue();

            if(customerName.isEmpty() || address.isEmpty() || postalCode.isEmpty() || phoneNumber.isEmpty() || countryCBox.getSelectionModel().getSelectedItem() == null || firstLevelDivisionCBox.getSelectionModel().getSelectedItem() == null){

                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error Dialog");
                errorAlert.setContentText("Please check for empty or incorrect input for all the fields");
                errorAlert.showAndWait();

            }else{
                int divisionID = division.getDivisionID();
                CustomerDaoImpl.addCustomer(customerName, address, postalCode, phoneNumber, createDate, createdBy, lastUpdate, lastUpdateBy, divisionID);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Customer created successfully.");
                alert.showAndWait();

                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/Customer.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }

        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to create customer.");
            e.printStackTrace();
            alert.showAndWait();
        }
}


    /** This method loads the first level division list according to selected country
     * @param event
     */
    @FXML
    void onActionCountryCBox(ActionEvent event) throws SQLException {

        firstLevelDivisionCBox.setDisable(false);

        if (countryCBox.getSelectionModel().getSelectedIndex() == 0) {
            firstLevelDivisionCBox.setItems(FirstLevelDivisionsDaoImpl.getAllUSDivisions());
        }
        else if (countryCBox.getSelectionModel().getSelectedIndex() == 1) {
            firstLevelDivisionCBox.setItems(FirstLevelDivisionsDaoImpl.getAllUKDivisions());
        }
        else if (countryCBox.getSelectionModel().getSelectedIndex() == 2) {
            firstLevelDivisionCBox.setItems(FirstLevelDivisionsDaoImpl.getAllCanadaDivisions());
        }
    }

    @FXML
    void onActionFirstLevelDivisionCBox(ActionEvent event) {

    }
}
