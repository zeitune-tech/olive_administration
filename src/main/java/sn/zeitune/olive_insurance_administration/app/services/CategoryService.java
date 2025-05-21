package sn.zeitune.olive_insurance_administration.app.services;

import sn.zeitune.olive_insurance_administration.app.dto.requests.CategoryRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.CategoryResponseDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    CategoryResponseDTO create(CategoryRequestDTO dto);
    CategoryResponseDTO update(UUID uuid, CategoryRequestDTO dto);
    void delete(UUID uuid);
    CategoryResponseDTO getByUuid(UUID uuid);
    List<CategoryResponseDTO> getAll();
}
