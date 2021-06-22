package code.controller;

import code.controller.request.ChangePasswordRequest;
import code.controller.request.LoginRequest;
import code.controller.request.RegisterRequest;
import code.controller.response.UserInfoResponse;
import code.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService userService;

    /*
    * REGISTER PAGE*/
    @PostMapping(value = "/register")
    public String register(@RequestBody RegisterRequest regisRequest) throws Exception {
        String result = userService.registerService(regisRequest);
        return result;
    }

    /*
    LOGIN PAGE*/
//    @GetMapping(value = "/login")
//    public String getLoginForm(Model model){
//        model.addAttribute("loginRequest", new LoginRequest());
//        return "/login";
//    }
    @PostMapping(value = "/login")
    public String login(@RequestBody LoginRequest loginRequest) throws Exception {
        String result = userService.loginService(loginRequest);
        return result;
    }

    @PutMapping(value = "/changePass")
    public String changePass( @RequestBody ChangePasswordRequest changePassReq,Integer userId) {
        return userService.changePass(changePassReq,userId);
    }

    @DeleteMapping(value = "/deleteUser")
    public String deleteUser(@RequestBody Integer userId) {
        return userService.deleteUser(userId);
    }

    @GetMapping(value = "/userInfo")
    public Object getUserDetail(@RequestBody Integer userId) throws Exception {
        UserInfoResponse show = userService.getUserInfo(userId);
        return show;
    }

}
