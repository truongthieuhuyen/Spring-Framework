package code.controller.response;

import code.controller.request.LoginRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm extends LoginRequest {
    public String phoneNumber;
    private String password;
}
