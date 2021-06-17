package code.controller;

import code.controller.request.ChangePasswordRequest;
import code.controller.request.LoginRequest;
import code.controller.request.RegisterRequest;
import code.controller.response.UserInfoResponse;
import code.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping(value = "/register")
    public String register(@RequestBody RegisterRequest regisRequest) throws Exception {
        String result = userService.registerService(regisRequest);
        return result;
    }

    @PostMapping(value = "/login")
    public String login(@RequestBody LoginRequest loginRequest) throws Exception {
        String result = userService.loginService(loginRequest);
        return result;
    }

    @PutMapping(value = "/changePass")
    public String changePass( @RequestBody ChangePasswordRequest changePassReq) {
        return userService.changePass(changePassReq);
    }

    @DeleteMapping(value = "/deleteUser/{user_id}")
    public String deleteUser(@PathVariable(value = "user_id") Integer userId) {
        return userService.deleteUser(userId);
    }

    @GetMapping(value = "/{user_id}")
    public Object getUserDetail(@PathVariable(value = "user_id") Integer userId) throws Exception {
        UserInfoResponse show = userService.getUserInfo(userId);
        return show;
    }

}
