package sn.zeitune.olive_insurance_administration.app.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.zeitune.olive_insurance_administration.app.dto.requests.CompanyLevelOrganizationRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.CompanyLevelOrganizationResponseDTO;

import java.util.UUID;

public interface CompanyLevelOrganizationService {
    CompanyLevelOrganizationResponseDTO create(CompanyLevelOrganizationRequestDTO dto, UUID companyId);
    CompanyLevelOrganizationResponseDTO update(UUID uuid, CompanyLevelOrganizationRequestDTO dto);
    void delete(UUID uuid);
    CompanyLevelOrganizationResponseDTO getByUuid(UUID uuid);
    Page<CompanyLevelOrganizationResponseDTO> search(UUID companyUuid, String name, Pageable pageable);
}
