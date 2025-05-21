package sn.zeitune.olive_insurance_administration.app.dto.responses;

import sn.zeitune.olive_insurance_administration.enums.PointOfSaleType;

import java.util.Set;
import java.util.UUID;

public record BrokerPointOfSaleResponseDTO(
        UUID id,
        String name,
        String acronym,
        String email,
        String phone,
        String address,
        String logo,
        String fax,
        String gsm,
        PointOfSaleType typePointOfSale,
        Set<UUID> companyUuids,
        Set<String> companyNames,
        Set<UUID> companyLevelOrganizationUuids,
        Set<String> companyLevelOrganizationNames
) {}
