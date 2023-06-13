package model;

/** This class was created to define all data types and methods of contacts, according to the Database ERD. */
public class Contacts {

    private int contactID;
    private String contactName;
    private String email;

    public Contacts(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /** This is the contactID getter
     * @return the contactID
     */
    public int getContactID() {
        return contactID;
    }

    /** This is the contactID setter
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /** This is the contactName getter
     * @return contactName
     */
    public String getContactName() {
        return contactName;
    }

    /** This is the contactName setter
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /** This is the email getter
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /** This is the email setter
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return contactName;
    }
}
