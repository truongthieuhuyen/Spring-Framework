package code.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class BookForm {
    public Integer id;
    private String title;
    private String author;
    private Float price;
}
