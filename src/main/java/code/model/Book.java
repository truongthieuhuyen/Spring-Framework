package code.model;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Book {
    private String title;
//    private Integer category_id;
    private String author;
    private Float price;

    public Book(String title, String author, Float price) {
        this.title  = title;
        this.author = author;
        this.price = price;
    }
}
