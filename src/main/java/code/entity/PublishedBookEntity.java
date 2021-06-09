package code.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter@Setter
@Entity
@Table(name = "published_book")
public class PublishedBookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "published_id")
    private Integer publishedId;

    @Column(name = "book_id")
    private Integer bookId;


    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "posted_time")
    private Timestamp postedTime;

    @Column(name = "book_name")
    private String title;
}
