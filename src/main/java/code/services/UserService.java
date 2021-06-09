package code.services;

import code.controller.request.ChangePasswordRequest;
import code.controller.request.LoginRequest;
import code.controller.request.RegisterRequest;
import code.controller.response.UserInfoResponse;
import code.entity.UserEntity;
import code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        if (regisRequest.getPhoneNumber() == null || regisRequest.getPhoneNumber().isEmpty() || regisRequest.getPhoneNumber().length() != 10) {
            return "Invalid phone number"; // dùng regex
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
        String ur = userRepository.findByPhoneNumberParam(regisRequest.getPhoneNumber());
        while (ur != null) {

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
        String ul = userRepository.findUserByPhoneNumberAndPassword(loginRequest.getPhoneNumber(), loginRequest.getPassword());
        while (ul != null) {
            return "Login Successful";
        }
        return "Login failed\n Wrong number or password";
    }

    /*
     * change password
     * */

    public String changePass(ChangePasswordRequest changePassReq) {
        // Find user in DB
        UserEntity cp = userRepository.findUserByPhoneNumber(changePassReq.getPhoneNumber());
        while (cp != null) {
            if (cp.getPassword().length() < 8) {
                return "Password must contains at least 8 characters";
            }
            cp.setPassword(changePassReq.getPassword());
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
        if (du != null) {
            userRepository.delete(du);
            return "User has been deleted";
        }
        return "User does not exist ";
    }

    /*
    API xem thông tin user
    */
    public UserInfoResponse getUserInfo(Integer userId) {
        UserInfoResponse response = new UserInfoResponse();

        UserEntity data = userRepository.findByUserId(userId);
        data.setPassword("********");
        response.setUserData(data);
        response.setCode(200);
        response.setMessage("Success");
        return response;

    }
}
