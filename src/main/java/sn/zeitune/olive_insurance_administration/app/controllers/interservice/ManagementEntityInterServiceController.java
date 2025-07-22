package sn.zeitune.olive_insurance_administration.app.controllers.interservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.olive_insurance_administration.app.dto.responses.ManagementEntityResponse;
import sn.zeitune.olive_insurance_administration.app.services.ManagementEntityService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/interservices/management-entities")
@RequiredArgsConstructor
public class ManagementEntityInterServiceController {

    private final ManagementEntityService managementEntityService;

    @PostMapping
    public ResponseEntity<List<ManagementEntityResponse>> getAll(
            @RequestBody List<UUID> uuids
    ) {
        List<ManagementEntityResponse> managementEntities = managementEntityService.getAllByIds(uuids);

        return ResponseEntity.ok(managementEntities);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ManagementEntityResponse> getByUuid(
            @PathVariable UUID uuid
    ) {
        ManagementEntityResponse managementEntity = managementEntityService.get(uuid);

        return ResponseEntity.ok(managementEntity);
    }

}
