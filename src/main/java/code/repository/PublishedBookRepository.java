package code.repository;

import code.entity.PublishedBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PublishedBookRepository extends JpaRepository<PublishedBookEntity, Integer> {

    List<PublishedBookEntity> findAllByBookIdAndUserId(Integer bookId, Integer userId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE book SET book_description = :description, book_price_$ = :price, " +
            " book_picture = :picture, category_id = :categoryId WHERE book_id = :bookId")
    int updateBookUsingNativeModify(@Param("description") String description,
                                    @Param("price") Double price,
                                    @Param("picture") String picture,
                                    @Param("categoryId") Integer categoryId,
                                    @Param("bookId") Integer bookId);

    List<PublishedBookEntity> findByPublishedId(Integer publishedId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "delete from published_book where published_id = ?1 ;")
    void delete(Integer publishedId);
}
