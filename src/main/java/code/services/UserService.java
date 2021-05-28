package code.services;

import code.controller.request.LoginRequest;
import code.controller.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class UserService {
    @Autowired
    Connection connection;

    /*
     * API Register
     * */
    public String register(RegisterRequest register) {
        //check phone
        if (register.getPhoneNumber() == null || register.getPhoneNumber() == "" || register.getPhoneNumber().length() != 10) {
            return "Invalid phone number";
        }
        //check email
        if (!StringUtils.hasText(register.getEmail()) || !register.getEmail().endsWith("@gmail.com")) {
            return "Email must contains '@gmail.com'";
        }
        //check password
        if (register.getPassword().length() < 8 || !StringUtils.hasText(register.getEmail())) {
            return "Password must contains at least 8 characters";
        }
        //check in database
        String query = "Select * from spring.users " +
                "where phone_number ='" + register.getPhoneNumber() + "' or email ='" + register.getEmail() + "';";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                return "Email or phone number has been used";
            }
            query = "INSERT INTO `spring`.`users` (`phone_number`, `password`, `email`) " +
                    "VALUES ('" + register.getPhoneNumber() + "', '" + register.getPassword() + "', '" + register.getEmail() + "');";
            st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Register failed : internal error";
        }
        return "Register successful";
    }

    /*
     * API login
     */
    public String login(LoginRequest login) {
        if (login.getPhoneNumber().isEmpty() || login.getPassword().isEmpty()) {
            return "Please enter your password and phone number ";
        }
        //check in database
        String query = "select * from spring.users where phone_number = '" + login.getPhoneNumber() +
                "' and password ='" + login.getPassword() + "';";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                return "Login Successful";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Register failed : internal error";
        }
        return "Login failed\n Wrong number or password";
    }
}
