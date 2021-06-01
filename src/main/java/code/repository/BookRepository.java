package code.repository;

import code.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {
    /*SELECT * FROM book WHERE category_id= ;*/
    List<BookEntity> findAllByCategoryId(Integer categoryId);

    List<BookEntity> findAllByAuthor(String author);

    List<BookEntity> findAllByCategoryIdAndTitle(Integer categoryId, String title);

    List<BookEntity> findAllByAuthorAndTitle(String author, String title);

    /*@Query(nativeQuery = true, value = "SELECT * FROM book WHERE")
    List<BookEntity> findByNativeQuery();*/
}
