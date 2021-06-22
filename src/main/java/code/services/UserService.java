package code.services;

import code.controller.request.ChangePasswordRequest;
import code.controller.request.LoginRequest;
import code.controller.request.RegisterRequest;
import code.controller.response.UserInfoResponse;
import code.entity.UserEntity;
import code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService implements UserDetailsService {
    //    @Autowired
    //    Connection connection;
    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    /*
     * API Register
     * */
    public String registerService(RegisterRequest regisRequest) {
        //check validate phone
        String PHONE_PATTERN = "^((84|0)[3|5|7|8|9])+([0-9]{8})$";
        if (regisRequest.getPhoneNumber().matches(PHONE_PATTERN) == false) {
            return "Phone number must have '84' or '0' at the beginning." +
                    "Requires entering the correct phone number.";
        }
        /*if (regisRequest.getPhoneNumber() == null || regisRequest.getPhoneNumber().isEmpty() || regisRequest.getPhoneNumber().length() != 10) {
            return "Invalid phone number"; // dùng regex
        }*/
        //check validate email
        String EMAIL_PATTERN = "^[a-zA-Z][\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        /* if (!StringUtils.hasText(regisRequest.getEmail()) || !regisRequest.getEmail().endsWith("@gmail.com")) {
            return "Email must contains '@gmail.com'";
        }*/
        if (Pattern.matches(EMAIL_PATTERN, regisRequest.getEmail()) == false) {
            return "Email must contains '@', can lower/upper case letters and '-'. ";
        }
        //check validate password
        String PASS_PATTERN = "^[a-zA-Z0-9]{8,20}$";
        if (Pattern.matches(PASS_PATTERN, regisRequest.getPassword()) == false) {
            return "Password must contain at least 8 characters, can lower/upper case letters, numeric characters. ";
        }
        /*if (regisRequest.getPassword().length() < 8 || !StringUtils.hasText(regisRequest.getPassword())) {
            return "Password must contains at least 8 characters";
        }*/

        //check DB if the user has existed
        String ur = userRepository.findByPhoneNumberOrEmailParam(regisRequest.getPhoneNumber(), regisRequest.getEmail());
        while (ur != null) {
            return "Email or phone number has been used";
        }
        //insert new user
        UserEntity insertUser = new UserEntity();
        insertUser.setPhoneNumber(regisRequest.getPhoneNumber());
        insertUser.setEmail(regisRequest.getEmail());
            //encode password
        insertUser.setPassword(bCryptPasswordEncoder.encode(regisRequest.getPassword()));
        insertUser.setName(regisRequest.getName());
            // set role default = role_member
        insertUser.setRole("role_member");
        UserEntity uname = userRepository.findUserByName(regisRequest.getName());
        // check user_name existed or not
        if (uname != null) {
            return "user name has been used";
        }
        userRepository.save(insertUser);

        return "Register successful";
    }


    /*
     * API login
     */
    public String loginService(LoginRequest loginRequest) {
        //check validate email & password
        if (loginRequest.getEmail().isEmpty() || loginRequest.getPassword().isEmpty()) {
            return "Please enter your password and email ";
        }
        //check DB if user has existed
        String ul = userRepository.findUserByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
        while (ul != null) {
            return "Login Successful";
        }
        return "Login failed\n Wrong email or password";
    }

    /*
     * change password
     * */

    public String changePass(ChangePasswordRequest changePassReq,Integer userId) {
        // Find user in DB
        UserEntity cp = userRepository.findUserByUserId(userId);
        while (cp != null) {
            String PASS_PATTERN = "^[a-zA-Z0-9]{8,20}$";
            if (Pattern.matches(PASS_PATTERN, changePassReq.getPassword()) == false) {
                return "Password must contain at least 8 characters, can lower/upper case letters, numeric characters. ";
            }
            cp.setPassword(bCryptPasswordEncoder.encode(changePassReq.getPassword()));
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
        data.setPassword("****");
        response.setUserData(data);
        response.setCode(200);
        response.setMessage("Success");
        return response;

    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserByName(s);
        if (userEntity == null) {
            throw new RuntimeException("User " + s + " not found");
        }
        List<GrantedAuthority> authorityList = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority(userEntity.getRole());
        authorityList.add(authority);

        UserDetails userDetails = new User(userEntity.getName(), userEntity.getPassword(), authorityList);

        return userDetails;
    }
}
