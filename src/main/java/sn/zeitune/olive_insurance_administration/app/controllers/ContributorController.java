package sn.zeitune.olive_insurance_administration.app.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.olive_insurance_administration.app.dto.requests.ContributorRequest;
import sn.zeitune.olive_insurance_administration.app.dto.requests.ContributorTypeRequest;
import sn.zeitune.olive_insurance_administration.app.dto.responses.ContributorResponse;
import sn.zeitune.olive_insurance_administration.app.dto.responses.ContributorTypeResponse;
import sn.zeitune.olive_insurance_administration.app.services.ContributorService;
import sn.zeitune.olive_insurance_administration.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/contributors")
@RequiredArgsConstructor
public class ContributorController {

    private final ContributorService contributorService;

    @PostMapping
    public ResponseEntity<ContributorResponse> create(
            @RequestBody @Valid ContributorRequest request,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        ContributorResponse response = contributorService.create(request, employee.getManagementEntity());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/types")
    public ResponseEntity<ContributorTypeResponse> createContributorType(
            Authentication authentication,
            @RequestBody ContributorTypeRequest request
    ) {
        if (authentication == null) {
            return ResponseEntity.badRequest().build();
        }

        UUID managementEntity = ((Employee) authentication.getPrincipal()).getManagementEntity();
        ContributorTypeResponse contributorTypeResponse = contributorService.createContributorType(request, managementEntity);

        return ResponseEntity.ok(contributorTypeResponse);
    }

    @GetMapping("/types")
    public ResponseEntity<List<ContributorTypeResponse>> getAllContributorTypes(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.badRequest().build();
        }

        UUID managementEntity = ((Employee) authentication.getPrincipal()).getManagementEntity();
        List<ContributorTypeResponse> contributorTypes = contributorService.getAllContributorTypes(managementEntity);

        return ResponseEntity.ok(contributorTypes);
    }


    @GetMapping("/types/{uuid}")
    public ResponseEntity<ContributorTypeResponse> getContributorTypeByUuid(
            Authentication authentication,
            @PathVariable UUID uuid) {
        if (authentication == null) {
            return ResponseEntity.badRequest().build();
        }

        ContributorTypeResponse contributorTypeResponse = contributorService.getContributorTypeByUuid(uuid);

        return ResponseEntity.ok(contributorTypeResponse);
    }


    @GetMapping("/{uuid}")
    public ResponseEntity<ContributorResponse> getByUuid(@PathVariable UUID uuid) {
        ContributorResponse response = contributorService.getByUuid(uuid);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/types/{uuid}")
    public ResponseEntity<ContributorTypeResponse> updateContributorType(
            Authentication authentication,
            @PathVariable UUID uuid,
            @RequestBody ContributorTypeRequest request) {
        if (authentication == null) {
            return ResponseEntity.badRequest().build();
        }

        UUID managementEntity = ((Employee) authentication.getPrincipal()).getManagementEntity();
        ContributorTypeResponse updatedContributorType = contributorService.updateContributorType(request, managementEntity, uuid);

        return ResponseEntity.ok(updatedContributorType);
    }

    @GetMapping
    public ResponseEntity<List<ContributorResponse>> getAll(Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        List<ContributorResponse> responses = contributorService.getAll();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        contributorService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
