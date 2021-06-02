package code.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "book")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "book_name")
    private String title;

    @Column(name = "book_picture")
    private String picture;

    @Column(name = "book_year")
    private String year;

    @Column(name = "book_description")
    private String description;

    @Column(name = "book_author")
    private String author;

    @Column(name = "book_price_$")
    private Double price;

    @Column(name = "category_id")
    private Integer categoryId;
}
