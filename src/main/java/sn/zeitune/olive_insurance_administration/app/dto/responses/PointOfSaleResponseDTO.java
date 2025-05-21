package sn.zeitune.olive_insurance_administration.app.dto.responses;

import sn.zeitune.olive_insurance_administration.enums.PointOfSaleType;

import java.util.UUID;

public record PointOfSaleResponseDTO(
        UUID id,
        String name,
        String email,
        String phone,
        String address,
        PointOfSaleType typePointOfSale,
        String companyNameOrBroker
) {}

