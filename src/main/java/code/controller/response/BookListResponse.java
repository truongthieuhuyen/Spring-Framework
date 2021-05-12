package code.controller.response;

import code.model.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class BookListResponse {
    private Integer code;
    private String message;
    private List<Book> data;
}
