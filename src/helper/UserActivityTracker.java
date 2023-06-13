package helper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserActivityTracker {

    /** This method tracks user activity by recording all user log-in attempts, dates, and time stamps in a file named login_activity.txt.
     * @param username
     * @param message
     * @throws IOException
     */
    public static void logInReport(String username, String message) throws IOException {

        FileWriter fWriter = new FileWriter("login_activity.txt", true);
        PrintWriter outputFile = new PrintWriter(fWriter);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = now.format(formatter);

        outputFile.println("User " + username + message + formattedTimestamp);
        outputFile.close();
    }

}
