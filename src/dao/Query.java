package dao;

import static dao.DBConnection.*;
import java.sql.ResultSet;
import java.sql.Statement;

/** This class executes the queries. */
public class Query {

    private static String query;
    private static Statement statement;
    private static ResultSet result;

    /** This method creates SQL queries
     * @param q
     */
    public static void makeQuery(String q){
        query = q;
        try{

            statement = connection.createStatement();

            if(query.toLowerCase().startsWith("select"))
                result = statement.executeQuery(q);

            if(query.toLowerCase().startsWith("delete") || query.toLowerCase().startsWith("insert") || query.toLowerCase().startsWith("update"))
                statement.executeUpdate(q);

        } catch (Exception ex) {
            System.out.println("Error: "+ex.getMessage());
        }
    }

    /** This method returns the result from SQL queries
     * @return result
     */
    public static ResultSet getResult() {
        return result;
    }
}
