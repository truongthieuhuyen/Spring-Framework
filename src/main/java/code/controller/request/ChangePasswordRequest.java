package code.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {
//    private Integer userId;
    private String phoneNumber;// khong can thiet
    private String password;
}
