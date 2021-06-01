package code.repository;

import code.entity.FavoriteBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteBookRepository extends JpaRepository<FavoriteBookEntity, Integer> {
}
