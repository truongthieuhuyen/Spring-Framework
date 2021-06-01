package code.controller;

import code.controller.request.ChangePasswordRequest;
import code.controller.request.LoginRequest;
import code.controller.request.RegisterRequest;
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

        return userService.registerService(regisRequest);
    }

    @PostMapping(value = "/login")
    public String login(@RequestBody LoginRequest loginRequest) throws Exception {

        return userService.loginService(loginRequest);
    }

    @PutMapping(value = "/changePass/{user_id}")
    public String changePass(@PathVariable(name = "user_id") Integer userId,
                             @RequestBody ChangePasswordRequest changePassReq) {

        return userService.changePass(changePassReq);
    }

    @DeleteMapping(value = "/deleteUser/{user_id}")
    public String deleteUser(@PathVariable(name = "user_id") Integer userId) {
        return userService.deleteUser(userId);
    }
}
