package code.repository;

import code.entity.PublishedBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublishedBookRepository extends JpaRepository<PublishedBookEntity, Integer> {
}
