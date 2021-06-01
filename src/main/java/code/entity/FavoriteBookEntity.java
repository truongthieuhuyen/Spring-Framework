package code.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter@Setter
@Entity
@Table(name = "favorite_book")
public class FavoriteBookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_book_id")
    private String favoriteId;

    @Column(name = "book_id")
    private Integer book_id;


    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "liked_time")
    private Integer likedTime;
}
