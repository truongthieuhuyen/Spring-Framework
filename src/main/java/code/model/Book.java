package code.model;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Book {
    private int id;
    private String title;
//    private Integer category_id;
    private String author;
    private Float price;

    public Book(int id,String title, String author, Float price) {
        this.id = id;
        this.title  = title;
        this.author = author;
        this.price = price;
    }

}
