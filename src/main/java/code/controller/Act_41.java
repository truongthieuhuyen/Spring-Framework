package code.controller;

import code.connection.DBConnection;
import code.model.Book;
import code.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;
import sun.security.util.Password;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

@RestController
@RequestMapping(value = "/homebook")
public class Act_41 {
    //    Activity 41: Viết API cho màn hình register và màn hình login theo Logic sau
    @PostMapping(value = "/register")   //API Register
    public String register(@RequestBody User regisRequest) throws Exception {
        DBConnection db = DBConnection.getInstance();
        Connection con = db.getDBConnection();
        String query = "Select count(*) as C_email from spring.users where email ='" + regisRequest.getEmail() + "'";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            Integer C_email = rs.getInt("C_email");
            if (C_email == 0) {
                if (regisRequest.getPassword().length() >= 8 && regisRequest.getEmail().endsWith("@gmail.com")
                        && regisRequest.getPhoneNumber().length() == 10) {
                    String insert = "INSERT INTO `spring`.`users` (`phone_number`, `password`, `email`) " +
                            "VALUES ('" + regisRequest.getPhoneNumber() + "', '" + regisRequest.getPassword() + "', '" + regisRequest.getEmail() + "');";
                    st.executeUpdate(insert);
                    System.out.println("Successfully inserted user information ");
                    regisRequest.setPassword("********");
                    return "Sign up success";
                } else if (regisRequest.getPhoneNumber().length() != 10) {
                    return "Phone number must contain at least 10 numbers: " + regisRequest.getPhoneNumber();
                } else if (regisRequest.getPassword().length() < 8) {
                    return "Password must contain at least 8 characters";
                } else if (!(regisRequest.getEmail().endsWith("@gmail.com"))) {
                    return "Email must contain '@gmail.com' : " + regisRequest.getEmail();
                }
            } else if (C_email == 1) {
                System.out.println("Email already exists ");
                return "Email already exists: " + regisRequest.getEmail();
            }
        }
        return null;
    }

    @PostMapping(value = "/login")   //API Login
    public String login(@RequestBody User loginRequest) throws Exception {
        DBConnection db = DBConnection.getInstance();
        Connection con = db.getDBConnection();
        String query = "Select count(*) from users where phone_number = '" + loginRequest.getPhoneNumber() + "'";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            String phone_number = rs.getString("phone_number");
            String password = rs.getString("password");
            if (phone_number != loginRequest.getPhoneNumber()) {
                System.out.println("Phone number is not exists ");
                return "Phone number is not exists";
            } else if (phone_number == loginRequest.getPhoneNumber() && password == loginRequest.getPassword()) {
                System.out.println("Login success");
                return "You've successfully logged in";
            } else {
                System.out.println("Login failed");
                return "Wrong phone number or password!";
            }
        }
        return null;
    }

    @GetMapping(value = "/book/{category_id}") //API
    public List<Book> getCategories(@PathVariable(value = "category_id") Integer category_id) {
        return null;
    }
}