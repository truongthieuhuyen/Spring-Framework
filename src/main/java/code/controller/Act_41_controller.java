//package code.controller;
//
//import code.controller.request.LoginRequest;
//import code.controller.request.RegisterRequest;
//import code.model.User;
//import code.services.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.Statement;
//
//@RestController
//@RequestMapping(value = "/user")
//public class Act_41_controller {
//    //    Activity 41: Viết API cho màn hình register và màn hình login theo Logic sau
//
//    @Autowired
//    UserService userService;
//
//    @PostMapping(value = "/register")   //API Register
//    public String register(@RequestBody RegisterRequest regisRequest) throws Exception {
//        String result = userService.register(regisRequest);
//        return result;
//    }
//
//    @PostMapping(value = "/login")   //API Login
//    public String login(@RequestBody LoginRequest loginRequest) throws Exception {
//        String result = userService.login(loginRequest);
//        return result;
//    }
//
//}