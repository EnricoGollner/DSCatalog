package dev.enricogollner.dscatalog.services;

import dev.enricogollner.dscatalog.dtos.CategoryDTO;
import dev.enricogollner.dscatalog.entities.Category;
import dev.enricogollner.dscatalog.repositories.CategoryRepository;
import dev.enricogollner.dscatalog.services.exceptions.DatabaseExcecption;
import dev.enricogollner.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {
    private final CategoryRepository repository;
    private Long id;

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
                () -> new ResourceNotFoundException("Categoria não encontrada"));
        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        Category entity = new Category();
        entity.setName(dto.getName());
        entity = repository.save(entity);
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        try {
            Category entity = repository.getReferenceById(id);
            entity.setName(dto.getName());
            entity = repository.save(entity);
            return new CategoryDTO(entity);
        } catch (Exception exception) {
            throw new ResourceNotFoundException("Não foi encontrada nenhuma categoria com id: " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Não foi encontrada nenhuma categoria com id: " + id);
        }
        try {
            repository.deleteById(id);
        } catch(DataIntegrityViolationException exception) {
            throw new DatabaseExcecption("Falha de integridade referencial");
        }
    }
}
