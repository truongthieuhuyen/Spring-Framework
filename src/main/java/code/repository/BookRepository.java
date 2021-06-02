package code.repository;

import code.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {
    /*SELECT * FROM book WHERE category_id= ;*/
    List<BookEntity> findAllByCategoryId(Integer categoryId);

    @Query(nativeQuery = true, value = "select * from book where book_name like %:title%")
    List<BookEntity> findAllByTitle(@Param("title") String title);

    @Query(nativeQuery = true, value = "select * from book where book_name like %:title% or book_author like %:author% ")
    List<BookEntity> findListBookByTitleOrAuthor(@Param("title") String title,
                                                 @Param("author") String author);

    BookEntity findAllByAuthorAndTitle(String author, String title);

    /*@Query(nativeQuery = true, value = "SELECT * FROM book WHERE")
    List<BookEntity> findByNativeQuery();*/


}
