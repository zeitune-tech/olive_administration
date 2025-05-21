package sn.zeitune.olive_insurance_administration.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.zeitune.olive_insurance_administration.app.dto.responses.ManagementEntityResponse;
import sn.zeitune.olive_insurance_administration.app.services.ManagementEntityService;
import sn.zeitune.olive_insurance_administration.security.Employee;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/app/management-entities")
@RequiredArgsConstructor
public class ManagementEntityController {

    private final ManagementEntityService managementEntityService;

    @GetMapping
    public ManagementEntityResponse get(
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        return managementEntityService.get(employee.getManagementEntity());
    }


    @GetMapping("/logo")
    public ResponseEntity<Resource> getLogo(
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();

        ManagementEntityResponse managementEntityResponse = managementEntityService.get(employee.getManagementEntity());
        String path = managementEntityResponse.logo();
        try {
            Path filePath = Paths.get(path);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                String contentType = Files.probeContentType(filePath);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
