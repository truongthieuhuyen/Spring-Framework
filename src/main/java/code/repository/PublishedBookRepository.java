package code.repository;

import code.entity.PublishedBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface PublishedBookRepository extends JpaRepository<PublishedBookEntity, Integer> {

    List<PublishedBookEntity> findAllByTitleAndUserId(String title, Integer userId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE book SET book_name = :title, book_description = :description, book_author = :author, " +
            "book_price_$ = :price, book_year = :year, book_picture = :picture, category_id = :categoryId WHERE book_id = :bookId")
    int updateBookUsingNativeModify(@Param("title") String title,
                                    @Param("description") String description,
                                    @Param("author") String author,
                                    @Param("price") Double price,
                                    @Param("year") String year,
                                    @Param("picture") String picture,
                                    @Param("categoryId") Integer categoryId,
                                    @Param("bookId") Integer bookId);

    List<PublishedBookEntity> findByPublishedId(Integer publishedId);

    @Modifying
    @Query(nativeQuery = true,value = "delete from published_book where published_id = ?1 ;")
    void delete(Integer publishedId);
}
