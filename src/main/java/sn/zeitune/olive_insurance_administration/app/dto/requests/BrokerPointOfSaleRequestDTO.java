package sn.zeitune.olive_insurance_administration.app.dto.requests;

import sn.zeitune.olive_insurance_administration.enums.PointOfSaleType;

public record BrokerPointOfSaleRequestDTO(
        String name,
        String acronym,
        String email,
        String phone,
        String address,
        String logo,
        String fax,
        String gsm,
        PointOfSaleType typePointOfSale
) {}
