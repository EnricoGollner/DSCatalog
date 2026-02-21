package dev.enricogollner.dscatalog.test;

import dev.enricogollner.dscatalog.dtos.ProductDTO;
import dev.enricogollner.dscatalog.entities.Category;
import dev.enricogollner.dscatalog.entities.Product;

import java.time.Instant;

public class Factory {

    public static Product createProduct() {
        Product product = new Product(1L, "Notebook", "MacBook M3 Pro designed by apple in california", 14999.99,
                "https://www.java.com/jcom/images/java-logo.png", Instant.parse("2026-02-21T00:59:00Z"));
        product.getCategories().add(new Category(2L, "Eletronics"));

        return product;
    }

    public static ProductDTO createProductDTO() {
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }

}
