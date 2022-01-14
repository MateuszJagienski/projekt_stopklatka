package stopklatka.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public Connection databaseLink;

    public Connection getConnection() {
        String databaseName = "stopklatka";
        String databaseUser = "stopklatka";
        String databasePassword = "jh..A221qank";
        String url = "jdbc:mysql://192.166.219.220:3306/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e ) {
            e.printStackTrace();
        }

        return databaseLink;
    }



}
