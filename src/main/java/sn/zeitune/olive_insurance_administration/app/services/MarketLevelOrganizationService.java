package sn.zeitune.olive_insurance_administration.app.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.zeitune.olive_insurance_administration.app.dto.requests.MarketLevelOrganizationRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.MarketLevelOrganizationResponseDTO;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface MarketLevelOrganizationService {
    MarketLevelOrganizationResponseDTO create(MarketLevelOrganizationRequestDTO dto);
    MarketLevelOrganizationResponseDTO update(UUID uuid, MarketLevelOrganizationRequestDTO dto);
    void delete(UUID uuid);
    MarketLevelOrganizationResponseDTO getByUuid(UUID uuid);
    List<MarketLevelOrganizationResponseDTO> getAll();
    Page<MarketLevelOrganizationResponseDTO> getAll(Pageable pageable);

    Page<MarketLevelOrganizationResponseDTO> search(String name, String email, String acronym, Pageable pageable);
    void assignCompanies(UUID organizationUuid, Set<UUID> companyUuids);
    void removeCompany(UUID orgUuid, UUID companyUuid);

    Page<MarketLevelOrganizationResponseDTO> getLinkedMarketLevelOrganizations(UUID managementEntity, Pageable pageable);


    MarketLevelOrganizationResponseDTO getByName(String poolTransportsPublicsDeVoyageurs);
}
