package code.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
//    private DBConnection(){
//    }
//
//    static Connection conn = null;
//
//    public static Connection getDBConnection(){
//        try {
//            if (conn == null){
//                Class.forName("com.mysql.jdbc.Driver");
//                conn = DriverManager.getConnection("jbdc:mysql://localhost:3306/spring","root","duongmich");
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }return conn;
//    }

    private DBConnection() {
    }

    private static DBConnection connector;
    private static Connection conn;

    public static DBConnection getInstance() {
        if (connector == null) {
            connector = new DBConnection();
        }
        return connector;
    }

    public Connection getDBConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if (conn == null) {
                conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/spring", "root", "duongmich");
            } else return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
