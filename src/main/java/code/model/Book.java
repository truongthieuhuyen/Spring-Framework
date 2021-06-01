package code.model;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Book {
    private int book_id;
    private String title;
//    private Integer category_id;
    private String author;
    private Float price;

    public Book(int book_id, String title, String author, Float price) {
        this.book_id = book_id;
        this.title  = title;
        this.author = author;
        this.price = price;
    }

}
