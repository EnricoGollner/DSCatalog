package dev.enricogollner.dscatalog.repositories;

import dev.enricogollner.dscatalog.entities.Product;
import dev.enricogollner.dscatalog.test.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    private long existingId;
    private long nonExisitingId;
    private long countTotalProducts;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExisitingId = 1000L;
        countTotalProducts = 25L;
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        repository.deleteById(existingId);

        Optional<Product> product = repository.findById(existingId);

        Assertions.assertFalse(product.isPresent());
    }

    @Test
    public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
        Product product = Factory.createProduct();
        product.setId(null);

        repository.save(product);

        Assertions.assertEquals(countTotalProducts + 1, product.getId());
    }

    @Test
    public void findByIdShouldReturnOptionalObjectWhenIdExists() {
        Optional<Product> product = repository.findById(existingId);

        Assertions.assertTrue(product.isPresent());
    }

    @Test
    public void findByIdShouldNonReturnOptionalObjectWhenIdDoesNotExist() {
        Optional<Product> product = repository.findById(nonExisitingId);

        Assertions.assertTrue(product.isEmpty());
    }

}
