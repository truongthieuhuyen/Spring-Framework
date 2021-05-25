package code.controller;

import code.controller.request.LoginRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/home")
public class HomeController {

//    Example
    @RequestMapping(value = "/current-time", method = RequestMethod.GET)
    public String getServerTime() {
        return String.valueOf(new Date());
    }

    @GetMapping(value = "/current-time2")
    public String getServerTime2() {
        return "API-2: " + String.valueOf(new Date());
    }

    @PostMapping(value = "/login")
    public LoginRequest login(@RequestBody LoginRequest loginRequest) {
        String hardUserName = "truongthieuhuyen";
        String hardPassword = "12345678";
        if (loginRequest.getPhoneNumber().equals(hardUserName) && loginRequest.getPassword().equals(hardPassword)) {
            loginRequest.setPassword("******");
            return loginRequest;
        } else {
            return loginRequest;
        }
    }

    @PutMapping(value = "/login")
    public LoginRequest loginPut(@RequestBody LoginRequest loginRequest) {
        String hardUserName = "truongthieuhuyen";
        String hardPassword = "987654321";
        if (loginRequest.getPhoneNumber().equals(hardUserName) && loginRequest.getPassword().equals(hardPassword)) {
            loginRequest.setPassword("******");
            return loginRequest;
        } else {
            return loginRequest;
        }
    }

    @DeleteMapping(value = "/user")
    public String deleteUser() {
        return "Delete success";
    }

    @GetMapping(value = "/news/{newId}")
    public String getNewsById(@PathVariable(value = "newId") String newId) {
        //truy van database lay chi tiet bai bao co Id = newId
        return "Content news : " + newId;
    }

    @GetMapping(value = "/news")
    public String getNewsByParams(@RequestParam(value = "id") String newId,
                                  @RequestParam(value = "language") String lang) {
        //truy van database lay chi tiet bai bao co Id = newId & langguage = lang
        return "Content news get by params : " + newId + ", lang: " + lang;
    }

    @GetMapping(value = "/new24h/{newName}")
    public String getNewsByString(@PathVariable(value = "newName") String name) {
        String id = name.substring(name.lastIndexOf("-"));
        return "Content : " + id;
    }
}

