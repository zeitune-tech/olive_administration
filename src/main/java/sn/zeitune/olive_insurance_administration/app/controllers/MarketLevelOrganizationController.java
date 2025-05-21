package sn.zeitune.olive_insurance_administration.app.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.olive_insurance_administration.app.dto.requests.AssignCompaniesToOrganizationRequest;
import sn.zeitune.olive_insurance_administration.app.dto.requests.MarketLevelOrganizationRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.MarketLevelOrganizationResponseDTO;
import sn.zeitune.olive_insurance_administration.app.services.MarketLevelOrganizationService;
import sn.zeitune.olive_insurance_administration.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/market-level-organizations")
@RequiredArgsConstructor
public class MarketLevelOrganizationController {

    private final MarketLevelOrganizationService service;

    @GetMapping("/linked")
    public ResponseEntity<Page<MarketLevelOrganizationResponseDTO>> getLinkedMarketLevelOrganizations(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String acronym,
            @PageableDefault(size = 10, sort = "name") Pageable pageable,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        return ResponseEntity.ok(service.getLinkedMarketLevelOrganizations(employee.getManagementEntity(), pageable));
    }


    @PatchMapping("/{uuid}/assign-companies")
    public ResponseEntity<Void> assignCompanies(
            @PathVariable UUID uuid,
            @Valid @RequestBody AssignCompaniesToOrganizationRequest request
    ) {
        service.assignCompanies(uuid, request.companyUuids());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{orgUuid}/companies/{companyUuid}")
    public ResponseEntity<Void> removeCompanyAssociation(
            @PathVariable UUID orgUuid,
            @PathVariable UUID companyUuid
    ) {
        service.removeCompany(orgUuid, companyUuid);
        return ResponseEntity.noContent().build();
    }



    @PutMapping("/{uuid}")
    public ResponseEntity<MarketLevelOrganizationResponseDTO> update(
            @PathVariable UUID uuid,
            @Valid @RequestBody MarketLevelOrganizationRequestDTO dto) {
        return ResponseEntity.ok(service.update(uuid, dto));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        service.delete(uuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<MarketLevelOrganizationResponseDTO> get(@PathVariable UUID uuid) {
        return ResponseEntity.ok(service.getByUuid(uuid));
    }

    @GetMapping("/list")
    public ResponseEntity<List<MarketLevelOrganizationResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping
    public ResponseEntity<Page<MarketLevelOrganizationResponseDTO>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String acronym,
            @PageableDefault(size = 10, sort = "name") Pageable pageable
    ) {
        return ResponseEntity.ok(service.search(name, email, acronym, pageable));
    }
}
