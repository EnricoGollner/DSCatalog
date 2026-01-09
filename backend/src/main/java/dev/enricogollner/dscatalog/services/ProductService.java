package dev.enricogollner.dscatalog.services;

import dev.enricogollner.dscatalog.dtos.CategoryDTO;
import dev.enricogollner.dscatalog.dtos.ProductDTO;
import dev.enricogollner.dscatalog.entities.Category;
import dev.enricogollner.dscatalog.entities.Product;
import dev.enricogollner.dscatalog.repositories.CategoryRepository;
import dev.enricogollner.dscatalog.repositories.ProductRepository;
import dev.enricogollner.dscatalog.services.exceptions.DatabaseExcecption;
import dev.enricogollner.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository repository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(Pageable pageable) {
        return repository.findAll(pageable).map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product product = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Categoria não encontrada"));
        return new ProductDTO(product, product.getCategories());
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        copyDTOToEntity(dto, entity);
        entity = repository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product entity = repository.getReferenceById(id);
            copyDTOToEntity(dto, entity);
            entity = repository.save(entity);
            return new ProductDTO(entity);
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

    private void copyDTOToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        entity.setDate(dto.getDate());

        entity.getCategories().clear();
        for (CategoryDTO catDTO : dto.getCategories()) {
            Category category = categoryRepository.getReferenceById(catDTO.getId());
            entity.getCategories().add(category);
        }
    }
}
