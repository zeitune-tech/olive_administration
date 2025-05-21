package sn.zeitune.olive_insurance_administration.app.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.olive_insurance_administration.app.dto.requests.AssignCompaniesToBrokerPointOfSaleRequest;
import sn.zeitune.olive_insurance_administration.app.dto.requests.CompanyPointOfSaleRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.PointOfSaleResponseDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.SimpleCompanyDTO;
import sn.zeitune.olive_insurance_administration.app.services.PointOfSaleService;
import sn.zeitune.olive_insurance_administration.enums.PointOfSaleType;
import sn.zeitune.olive_insurance_administration.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/points-of-sale")
@RequiredArgsConstructor
public class PointOfSaleController {

    private final PointOfSaleService service;


    @PostMapping
    public ResponseEntity<PointOfSaleResponseDTO> createCompany(
            @Valid @RequestBody CompanyPointOfSaleRequestDTO dto,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        return ResponseEntity.ok(service.createCompanyPdv(dto, employee.getManagementEntity()));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<PointOfSaleResponseDTO> get(@PathVariable UUID uuid) {
        return ResponseEntity.ok(service.getByUuid(uuid));
    }


    @GetMapping
    public ResponseEntity<Page<PointOfSaleResponseDTO>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) PointOfSaleType type,
            @PageableDefault(size = 10, sort = "name") Pageable pageable,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        return ResponseEntity.ok(service.searchByCompany( employee.getManagementEntity(), name, pageable));
    }


    @GetMapping("/brokers")
    public ResponseEntity<Page<PointOfSaleResponseDTO>> searchBrokers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) PointOfSaleType type,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(service.searchBrokers(name, type, pageable));
    }

    @GetMapping("/linked-brokers")
    public ResponseEntity<Page<PointOfSaleResponseDTO>> searchBrokersByCompany(
            @RequestParam UUID companyUuid,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) PointOfSaleType type,
            @PageableDefault(size = 10) Pageable pageable,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        return ResponseEntity.ok(service.searchBrokersByCompany(employee.getManagementEntity(), name, type, pageable));
    }

    @PatchMapping("/broker/{uuid}/assign-companies")
    public ResponseEntity<Void> assignCompaniesToBroker(
            @PathVariable UUID uuid,
            @Valid @RequestBody AssignCompaniesToBrokerPointOfSaleRequest request
    ) {
        service.assignCompaniesToBrokerPdv(uuid, request.companyUuids());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/broker/{brokerPdvUuid}/companies/{companyUuid}")
    public ResponseEntity<Void> removeCompanyFromBroker(
            @PathVariable UUID brokerPdvUuid,
            @PathVariable UUID companyUuid
    ) {
        service.removeCompanyFromBrokerPdv(brokerPdvUuid, companyUuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/broker/{uuid}/companies")
    public ResponseEntity<List<SimpleCompanyDTO>> getCompaniesOfBroker(@PathVariable UUID uuid) {
        return ResponseEntity.ok(service.getCompaniesOfBroker(uuid));
    }

}

