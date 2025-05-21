package sn.zeitune.olive_insurance_administration.app.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.olive_insurance_administration.app.dto.requests.ContributorRequest;
import sn.zeitune.olive_insurance_administration.app.dto.responses.ContributorResponse;
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

    @GetMapping("/{uuid}")
    public ResponseEntity<ContributorResponse> getByUuid(@PathVariable UUID uuid) {
        ContributorResponse response = contributorService.getByUuid(uuid);
        return ResponseEntity.ok(response);
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
