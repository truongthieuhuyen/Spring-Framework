package code.services;

import code.controller.request.ChangePasswordRequest;
import code.controller.request.LoginRequest;
import code.controller.request.RegisterRequest;
import code.entity.UserEntity;
import code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserService {
    //    @Autowired
    //    Connection connection;
    @Autowired
    UserRepository userRepository;

    /*
     * API Register
     * */
    public String registerService(RegisterRequest regisRequest) {
        //check valid phone
        if (regisRequest.getPhoneNumber() == null || regisRequest.getPhoneNumber() == "" || regisRequest.getPhoneNumber().length() != 10) {
            return "Invalid phone number";
        }
        //check valid email
        if (!StringUtils.hasText(regisRequest.getEmail()) || !regisRequest.getEmail().endsWith("@gmail.com")) {
            return "Email must contains '@gmail.com'";
        }
        //check valid password
        if (regisRequest.getPassword().length() < 8 || !StringUtils.hasText(regisRequest.getEmail())) {
            return "Password must contains at least 8 characters";
        }
        //check DB if the user has existed
        UserEntity ur = userRepository.findUserByPhoneNumber(regisRequest.getPhoneNumber());
        while (ur != null) { //bị ngược logic
            return "Email or phone number has been used";
        }
        //insert new user
        UserEntity insertUser = new UserEntity();
        insertUser.setPhoneNumber(regisRequest.getPhoneNumber());
        insertUser.setEmail(regisRequest.getEmail());
        insertUser.setPassword(regisRequest.getPassword());
        userRepository.save(insertUser);
        return "Register successful";
    }


    /*
     * API login
     */
    public String loginService(LoginRequest loginRequest) {
        //check valid phone & password
        if (loginRequest.getPhoneNumber().isEmpty() || loginRequest.getPassword().isEmpty()) {
            return "Please enter your password and phone number ";
        }
        //check DB if user has existed
        UserEntity ul = userRepository.findUserByPhoneNumberAndPassword(loginRequest.getPhoneNumber(), loginRequest.getPassword());
        while (ul != null) { // ngược logic
            return "Login Successful";
        }
        return "Login failed\n Wrong number or password";
    }

    /*
     * change password
     * */
    public String changePass(ChangePasswordRequest userCP) {
        // Find user in DB
        UserEntity cp = userRepository.findUserByUserId(userCP.getUserId());
        if (cp != null) { // đúng logic
            cp.setPassword(userCP.getPassword());
            userRepository.save(cp);
            return "Your password has been changed";
        }
        return "User does not exist ";
    }

    /*
     * delete user
     * */
    public String deleteUser(Integer userId) {
        UserEntity du = userRepository.findUserByUserId(userId);
        if (du != null) { //logic ok
            userRepository.delete(du);
            return "Deleted user";
        }
        return "User does not exist ";
    }
}
