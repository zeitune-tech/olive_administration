package sn.zeitune.olive_insurance_administration.app.services;

import sn.zeitune.olive_insurance_administration.app.dto.requests.CompanyRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.CompanyResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.UUID;

public interface CompanyService {

    Page<CompanyResponseDTO> search(String name, String acronym, String email, Pageable pageable);
    CompanyResponseDTO create(CompanyRequestDTO dto);
    CompanyResponseDTO update(UUID uuid, CompanyRequestDTO dto);
    void delete(UUID uuid);
    CompanyResponseDTO getByUuid(UUID uuid);
    List<CompanyResponseDTO> getAll();

    CompanyResponseDTO getByName(String name);

    Page<CompanyResponseDTO> getAllLinkedCompanies(UUID managementEntity, Pageable pageable);
}
