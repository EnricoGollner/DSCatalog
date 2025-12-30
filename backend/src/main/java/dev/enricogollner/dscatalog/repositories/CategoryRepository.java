package dev.enricogollner.dscatalog.repositories;

import dev.enricogollner.dscatalog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
