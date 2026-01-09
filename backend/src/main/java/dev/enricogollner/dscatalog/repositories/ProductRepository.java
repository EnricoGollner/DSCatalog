package dev.enricogollner.dscatalog.repositories;

import dev.enricogollner.dscatalog.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository <Product, Long>{
}
