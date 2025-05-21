package sn.zeitune.olive_insurance_administration.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.olive_insurance_administration.app.dto.requests.CategoryRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.CategoryResponseDTO;
import sn.zeitune.olive_insurance_administration.app.entities.Category;
import sn.zeitune.olive_insurance_administration.app.exceptions.NotFoundException;
import sn.zeitune.olive_insurance_administration.app.mappers.CategoryMapper;
import sn.zeitune.olive_insurance_administration.app.repositories.CategoryRepository;
import sn.zeitune.olive_insurance_administration.app.services.CategoryService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponseDTO create(CategoryRequestDTO dto) {
        Category category = CategoryMapper.map(dto, new Category());

        // Check if a category with the same name already exists
        if (categoryRepository.existsByName(dto.name())) {
            throw new IllegalArgumentException("Category with the same name already exists");
        }
        return CategoryMapper.map(categoryRepository.save(category));
    }

    @Override
    public CategoryResponseDTO update(UUID uuid, CategoryRequestDTO dto) {
        Category category = categoryRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        CategoryMapper.map(dto, category);
        return CategoryMapper.map(categoryRepository.save(category));
    }

    @Override
    public void delete(UUID uuid) {
        Category category = categoryRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        categoryRepository.delete(category);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDTO getByUuid(UUID uuid) {
        Category category = categoryRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        return CategoryMapper.map(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> getAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::map)
                .collect(Collectors.toList());
    }
}
