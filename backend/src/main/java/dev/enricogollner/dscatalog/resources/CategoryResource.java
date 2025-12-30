package dev.enricogollner.dscatalog.resources;

import dev.enricogollner.dscatalog.dtos.CategoryDTO;
import dev.enricogollner.dscatalog.services.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryResource {
    private CategoryService service;

    public CategoryResource(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "sort", defaultValue = "name") String sort
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sort);

        Page<CategoryDTO> categories = service.findAllPaged(pageRequest);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        CategoryDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }
}
