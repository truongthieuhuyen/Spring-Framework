package code.controller.response;

import code.entity.BookEntity;
import code.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter@Setter
public class UserInfoResponse {
    private Integer code;
    private String message;
    private UserEntity userData;
}
