package code.controller;

import code.controller.request.LoginRequest;
import code.controller.response.LoginForm;
import code.controller.response.RegisterForm;
import code.model.Book;
import code.model.User;
import code.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = {"/user"})
public class Act_61_controller {

    @Autowired
    UserService userService;
    User user;

    @GetMapping(value = {"/register"})
    public String registerPage(Model model){
        RegisterForm registerForm = new RegisterForm();
        model.addAttribute("registerForm",registerForm);
        return "register";
    }
    @PostMapping(value = {"/register"})
    public String postRegisterForm(Model model, @ModelAttribute("registerForm") RegisterForm registerForm){
        String  phoneNumber = registerForm.getPhoneNumber();
        String password = registerForm.getPassword();
        String email = registerForm.getEmail();
        String result = userService.register(registerForm);
        return "redirect:/index2";
    }


    @GetMapping(value = {"/login"})
    public String loginPage(Model model) {
        LoginForm loginForm = new LoginForm();
        model.addAttribute("loginForm", loginForm);
        return "login";
    }
    @PostMapping(value = {"/login"})
    public String postLoginForm(Model model, @ModelAttribute("loginForm") LoginForm loginForm,
                                @RequestBody LoginRequest loginRequest){
        String phoneNumber = loginForm.getPhoneNumber();
        String password = loginForm.getPassword();
        String result = userService.login(loginForm);
        return "/index2";
    }
}
