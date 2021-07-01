package code.services;

import code.controller.request.ChangePasswordRequest;
import code.controller.request.LoginRequest;
import code.controller.request.RegisterRequest;
import code.controller.response.UserInfoResponse;
import code.entity.UserEntity;
import code.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class UserService implements UserDetailsService {
    private static final Integer expiredTime = 2 * 60;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

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
        logger.info("==================== START RECEIVED NEW REGISTER REQUEST ");

        //check validate phone
        String PHONE_PATTERN = "^((84|0)[3|5|7|8|9])+([0-9]{8})$";
        if (!regisRequest.getPhoneNumber().matches(PHONE_PATTERN)) {
            logger.error("Phone number [{}] is not valid", regisRequest.getPhoneNumber());
            return "Phone number must have '84' or '0' at the beginning." +
                    "\nRequires entering the correct phone number.";
        }

        //check validate email
        String EMAIL_PATTERN = "^[a-zA-Z][\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!Pattern.matches(EMAIL_PATTERN, regisRequest.getEmail())) {
            logger.error("Email [{}] is not valid", regisRequest.getEmail());
            return "Email must contains '@', can lower/upper case letters and '-'. ";
        }

        //check validate password
        String PASS_PATTERN = "^[a-zA-Z0-9]{8,20}$";
        if (!Pattern.matches(PASS_PATTERN, regisRequest.getPassword())) {
            logger.error("Password is not valid");
            return "Password must contain at least 8 characters, can lower/upper case letters, numeric characters. ";
        }

        //check DB if the user has existed
        String ur = userRepository.findByPhoneNumberOrEmailParam(regisRequest.getPhoneNumber(), regisRequest.getEmail());
        if (ur != null) {
            logger.error("Email [{}] or phone number [{}] has been used", regisRequest.getEmail(), regisRequest.getPhoneNumber());
            return "Email or phone number has been used";
        }
        //insert new user info
        UserEntity insertUser = new UserEntity();
        insertUser.setPhoneNumber(regisRequest.getPhoneNumber());
        logger.info("Save phone number [{}] into new user: ", regisRequest.getPhoneNumber());
        insertUser.setEmail(regisRequest.getEmail());
        logger.info("Save email [{}] into new user: ", regisRequest.getEmail());

        // check user_name existed or not
        UserEntity uname = userRepository.findUserByName(regisRequest.getName());
        if (uname != null) {
            logger.error("User name [{}] has been used", regisRequest.getName());
            return "User name has been used";
        }

        insertUser.setName(regisRequest.getName());
        logger.info("Save name [{}] into new user: ", regisRequest.getName());

        //encode + insert new password
        insertUser.setPassword(bCryptPasswordEncoder.encode(regisRequest.getPassword()));
        logger.info("Save password into new user");

        // set role default = role_member
        insertUser.setRole("ROLE_MEMBER");
        logger.info("Set new user role [{}]: ", "ROLE_MEMBER");

        userRepository.save(insertUser);
        logger.info("New user successfully saved with email [{}]: ", regisRequest.getEmail());
        logger.info("==================== END REQUEST REGISTER");
        return "Register successful";
    }


    /*
     * API login
     */
    public String loginService(LoginRequest loginRequest) {
        logger.info("==================== START RECEIVED LOGIN REQUEST from email [{}]", loginRequest.getEmail());

        //check validate email & password
        if (loginRequest.getEmail().isEmpty() || loginRequest.getPassword().isEmpty()) {
            logger.error("Password or email is empty");
            return "Please enter your password and email ";
        }

        //check DB if user has existed
        UserEntity ul = userRepository.findUserByEmail(loginRequest.getEmail());
        if (ul != null) {
            logger.info("Found user name [{}] with this email", ul.getName());

            //compare encrypted password between login password and DB password
            boolean check = bCryptPasswordEncoder.matches(loginRequest.getPassword(), ul.getPassword());
            if (!check) {
                logger.error("Password [{}] not match with name [{}]", loginRequest.getPassword(), ul.getName());
                return "Wrong password";

            }

            String token = UUID.randomUUID().toString();//create a random token string
            ul.setToken(token);//save token to user DB
            Timestamp timestamp = new Timestamp(System.currentTimeMillis() + expiredTime * 1000);
            ul.setExpiredTime(timestamp);//save expired time to DB
            logger.info("Created new token [{}] for user name [{}] ", ul.getToken(), ul.getName());
            userRepository.save(ul);//saved token for login session
            logger.info("Login success with email [{}] .", loginRequest.getEmail());
            logger.info("==================== END REQUEST LOGIN");
            return "Login Successful";
        }
        logger.error("Email [{}] not found", loginRequest.getEmail());
        return "Login failed\n Email is not correct";
    }

    /*
     * API change password
     * */

    public String changePass(ChangePasswordRequest changePassReq, Integer userId) {
        logger.info("==================== START RECEIVED NEW REQUEST CHANGE PASSWORD from email {}.", changePassReq.getEmail());

        // Find user in DB
        UserEntity cp = userRepository.findUserByUserId(userId);
        if (cp != null) {
            logger.info("Found user name {} with this email ", cp.getName());

            String PASS_PATTERN = "^[a-zA-Z0-9]{8,20}$";
            if (!Pattern.matches(PASS_PATTERN, changePassReq.getPassword())) {
                logger.error("Incorrect password syntax ");
                return "Password must contain at least 8 characters, can lower/upper case letters, numeric characters.";
            }
            cp.setPassword(bCryptPasswordEncoder.encode(changePassReq.getPassword()));
            userRepository.save(cp);
            logger.info("Password of user name {} has been changed", cp.getName());
            logger.info("==================== END REQUEST CHANGE PASSWORD ");
            return "Your password has been changed";
        }
        logger.error("User with email [{}] can't found", changePassReq.getEmail());
        return "User does not exist ";
    }

    /*
     * API delete user
     * */
    public String deleteUser(Integer userId) {
        logger.info("==================== START RECEIVED NEW REQUEST DELETE USER from userId [{}] .", userId);

        UserEntity du = userRepository.findUserByUserId(userId);
        if (du != null) {
            logger.info("Found user name [{}]", du.getName());
            userRepository.delete(du);
            logger.info("Deleted this user");
            logger.info("==================== END REQUEST ");
            return "User has been deleted";
        }
        logger.error("User id [{}] not found", userId);
        return "User does not exist ";
    }

    /*
    API xem thông tin user
    */
    public UserInfoResponse getUserInfo(Integer userId, String name) {
        logger.info("==================== START RECEIVED NEW REQUEST GET USER INFO from Id [{}]", userId);
        UserInfoResponse response = new UserInfoResponse();

        //find user in DB
        Timestamp now = new Timestamp(System.currentTimeMillis());
        UserEntity user = userRepository.findByUserId(userId);
        if (user.getExpiredTime().before(now)) {
            logger.error("Token of name [{}] has expired at [{}]", user.getName(), user.getExpiredTime());//token has expired ---> re-login
            response.setMessage(" Network Authentication Required\n Please re-login ");
            response.setUserData(null);
            response.setCode(511);
            return response;
        }

        //find name in DB
        UserEntity fname = userRepository.findUserByName(name);
        if (fname == null) {
            logger.error("Not found any user with name [{}]", name);//not found any user
            response.setMessage("Not Found");
            response.setUserData(null);
            response.setCode(404);
            return response;
        }

        fname.setPassword("****");//not allowed to see the password
        fname.setToken("****");//not allowed to see the token
        fname.setExpiredTime(null);//not allowed to see expiredTime
        response.setUserData(fname);
        response.setCode(200);
        response.setMessage("Success");
        logger.info("Shown user info");
        logger.info("==================== END REQUEST ");
        return response;
    }

    /*
     * API update rank account*/
    public String upRankAccount(Integer userId, String name) {
        logger.info("==================== START RECEIVED NEW REQUEST UP RANK ACCOUNT from user [{}]",userId);
        UserInfoResponse response = new UserInfoResponse();

        //check the role of user
        UserEntity role = userRepository.findUserByUserId(userId);
        if (!role.getRole().equals("ROLE_ADMIN")) {
            logger.error("UserId [{}] doesn't have permission", userId);
            return "Not admin permission";
        }

        //check user in DB
        UserEntity fname = userRepository.findUserByName(name);
        if (fname.getRole().equals("ROLE_VIP_MEMBER")) {
            logger.error("User [{}] is already a vip member", name);
            return "User " + name + " is already a vip member";
        }

        //update into DB
        fname.setRole("ROLE_VIP_MEMBER");
        userRepository.save(fname);
        logger.info("Set role of user [{}] to vip member successful",name);

        //return
        fname.setPassword("****");//not allowed to see the password
        fname.setToken("****");//not allowed to see the token
        fname.setExpiredTime(null);//not allowed to see expiredTime
        response.setUserData(fname);
        response.setCode(200);
        response.setMessage("Success");
        logger.info("==================== END REQUEST ");
        return "User " + name + " is a vip member from now on\n" + response;
    }


    /*
     * Liên quan đến phân quyền */
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
