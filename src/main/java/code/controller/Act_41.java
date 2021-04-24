package code.controller;

import code.connection.DBConnection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@RestController
@RequestMapping(value = "/homebook")
public class Act_41 {
    //    Activity 41: Viết API cho màn hình register và màn hình login theo Logic sau
    @PostMapping(value = "/register")   //API Register
    public String register(@RequestBody User regRequest) throws Exception {
        String PhoneNumber = "0327222822";
        String Password = "12345678";
        String Email = "abs17@gmail.com";

        if (regRequest.getPassword().length() >= 8 && regRequest.getEmail().endsWith("@gmail.com") && regRequest.getPhoneNumber().length() >= 10) {
            //            Connection conn = DBConnection.getDBConnection();
            DBConnection db = DBConnection.getInstance();
            Connection con = db.getDBConnection();
            String query = "Select email from users";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                if (Email.matches(regRequest.getEmail())) {
                    System.out.println("Email already exists : " + regRequest.getEmail());
                    return "Email already exists";
                } else {
                    String insert = "INSERT INTO `spring`.`users` (`phone_number`, `password`, `email`) " +
                            "VALUES ('" + regRequest.getPhoneNumber() + "', '" + regRequest.getPassword() + "', '" + regRequest.getEmail() + "');";
                    st.executeUpdate(insert);
                    System.out.println("Successfully inserted user information ");
                    return "Sign up success";
                }
            }
        } else if (regRequest.getPhoneNumber().length() < 10) {
            return "Phone number must contain at least 10 numbers: " + regRequest.getPhoneNumber();
        } else if (regRequest.getPassword().length() < 8) {
            return "Password must contain at least 8 characters";
        } else if (!(regRequest.getEmail().endsWith("@gmail.com"))) {
            return "Email must contain '@gmail.com' : " + regRequest.getEmail();
        }
        return null;
    }


    @PostMapping(value = "/login")   //API Login
    public String login(@RequestBody User logRequest) throws Exception {
        String PhoneNumber = "0327222822";
        String Password = "12345678";

        DBConnection db = DBConnection.getInstance();
        Connection con = db.getDBConnection();
        String query = "Select phone_number from users";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            if (!(logRequest.getPhoneNumber().matches(PhoneNumber))) {
                System.out.println("Phone number not exists ");
                return "Phone number not exists";
            } else if (PhoneNumber.matches(logRequest.getPhoneNumber()) && Password.matches(logRequest.getPassword())) {
                System.out.println("User login successfully");
                return "Login success";
            } else {
                System.out.println("Login failed");
                return "Wrong phone number or password!";
            }
        }
        return null;
    }
}

@Getter
@Setter
class User {
    private String phoneNumber;
    private String password;
    private String email;
}
