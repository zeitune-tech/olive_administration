package sn.zeitune.olive_insurance_administration.app.mappers;

import sn.zeitune.olive_insurance_administration.app.dto.requests.CategoryRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.CategoryResponseDTO;
import sn.zeitune.olive_insurance_administration.app.entities.Category;

public class CategoryMapper {

    public static Category map(CategoryRequestDTO dto, Category category) {
        category.setName(dto.name());
        category.setDescription(dto.description());
        return category;
    }

    public static CategoryResponseDTO map(Category category) {
        return new CategoryResponseDTO(
                category.getUuid(),
                category.getName(),
                category.getDescription()
        );
    }
}
