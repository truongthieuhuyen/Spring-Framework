//package code.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestTemplate;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//
//@Configuration
//public class ConnectionConfig {
//    @Value("${database.user}")
//    String user;
//    @Value("${database.pass}")
//    String pass;
//    @Value("${database.url}")
//    String url;
//
//    @Bean
//    Connection getDBConnection() {
//        Connection connection = null;
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            if (connection == null) {
//                connection = (Connection) DriverManager.getConnection(url, user, pass);
//            } else return connection;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return connection;
//    }
//
//
//
//}
