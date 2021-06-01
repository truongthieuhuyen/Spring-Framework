package code.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter@Setter
@Entity
@Table(name = "published_book")
public class PublishedBookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "published_book_id")
    private String publishedId;

    @Column(name = "book_id")
    private Integer book_id;


    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "posted_time")
    private Integer postedTime;
}
