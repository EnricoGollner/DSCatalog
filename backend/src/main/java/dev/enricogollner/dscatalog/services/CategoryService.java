package dev.enricogollner.dscatalog.services;

import dev.enricogollner.dscatalog.dtos.CategoryDTO;
import dev.enricogollner.dscatalog.entities.Category;
import dev.enricogollner.dscatalog.repositories.CategoryRepository;
import dev.enricogollner.dscatalog.services.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
        return repository.findAll(pageRequest).map(CategoryDTO::new);
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Category category = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Categoria não encontrada"));
        return new CategoryDTO(category);
    }
}
