package code.model;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Book {
    private String book_name;
    private Integer category_id;
    private String author;
    private Float price;
}
