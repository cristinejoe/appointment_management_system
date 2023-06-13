package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class was created to read files from Contacts database. */
public class ContactDaoImpl {

    /** This method retrieves all contacts from database
     * @return allContacts
     */
    public static ObservableList<Contacts> getAllContacts()throws SQLException {

        ObservableList<Contacts> allContacts = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Contacts";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            Contacts contact = new Contacts(contactId, contactName, email);
            allContacts.add(contact);

        }
        return allContacts;
    }

    /** This method retrieves all contacts by contact ID
     * @return contactsById
     */
    public static Contacts getContactByContactID(int contactID) throws SQLException {

        String sql = "SELECT * FROM Contacts where Contact_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, contactID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            Contacts contactsById = new Contacts(contactId, contactName, email);
            return contactsById;
        }
        return null;
    }

}
