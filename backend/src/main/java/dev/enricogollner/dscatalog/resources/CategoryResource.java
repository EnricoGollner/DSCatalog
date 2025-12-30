package dev.enricogollner.dscatalog.resources;

import dev.enricogollner.dscatalog.entities.Category;
import dev.enricogollner.dscatalog.repositories.CategoryRepository;
import dev.enricogollner.dscatalog.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryResource {
    private CategoryService service;

    public CategoryResource(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Category>> get() {
        List<Category> categories = service.findAll();
        return ResponseEntity.ok(categories);
    }
}
