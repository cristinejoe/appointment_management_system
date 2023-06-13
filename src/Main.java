import dao.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;

/** This is the main class which creates an app that is the scheduling desktop application. */
public class Main extends Application {


    //Lambdas can be found on lines #72 from ModifyAppointmentController and #120 from LoginController
    //javadoc files are inside a folder called 'javadoc" that is located in: 'Software II Project.zip\Software II Project\javadoc"
    /** This is the main method. This is the first method that is called when running this java program.
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Locale.setDefault(new Locale("fr"));

        // Calling the method from DBConnection to open database connection
        DBConnection.openConnection();

        /** This method is called to run the default init and then start method.
         * @param args
         */
        launch(args);

        // Calling the method from DBConnection to terminate database connection
        DBConnection.closeConnection();
    }

    /** This is the start method, it initializes the 'Login.fxml' file.
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
        primaryStage.setScene(new Scene(root, 400, 265));
        primaryStage.show();
    }

}
