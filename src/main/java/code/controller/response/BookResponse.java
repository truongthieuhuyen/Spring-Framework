package code.controller.response;

import code.entity.BookEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class BookResponse {
    private String message;
    private BookEntity data;
}
