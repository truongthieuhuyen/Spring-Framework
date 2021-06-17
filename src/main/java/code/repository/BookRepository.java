package code.repository;

import code.entity.BookEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {
    /*SELECT * FROM book WHERE category_id= ;*/
    List<BookEntity> findAllBy(String title, Pageable pageable);

    @Query(nativeQuery = true, value = "select * from book where book_name like %:title% order by :orderBy :order limit 2 offset :offset")
    List<BookEntity> findNativeAllByTitle(@Param("title") String title,
                                          @Param("orderBy") String orderBy,
                                          @Param("order") String order,
                                          @Param("offset") Integer offset);

    @Query(nativeQuery = true, value = "select * from book where book_name = :title and book_author = :author ")
    List<BookEntity> findAllBookByTitleAndAuthor(@Param("title") String title,
                                                 @Param("author") String author);
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "delete from book where book_id = ?1 ;")
    void delete(Integer bookId);

//    List<BookEntity> findAllByAuthorAndTitle(String author, String title);


}
