package sn.zeitune.olive_insurance_administration.app.mappers;

import sn.zeitune.olive_insurance_administration.app.dto.requests.MarketLevelOrganizationRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.MarketLevelOrganizationResponseDTO;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.MarketLevelOrganization;
import sn.zeitune.olive_insurance_administration.enums.ManagementEntityType;

public class MarketLevelOrganizationMapper {

    public static MarketLevelOrganization map(MarketLevelOrganizationRequestDTO dto, MarketLevelOrganization entity) {
        entity.setName(dto.name());
        entity.setAcronym(dto.acronym());
        entity.setEmail(dto.email());
        entity.setPhone(dto.phone());
        entity.setAddress(dto.address());
        entity.setLogo(dto.logo());
        entity.setFax(dto.fax());
        entity.setGsm(dto.gsm());
        entity.setType(ManagementEntityType.MARKET_LEVEL_ORGANIZATION); // Important !
        return entity;
    }

    public static MarketLevelOrganizationResponseDTO map(MarketLevelOrganization entity) {
        return new MarketLevelOrganizationResponseDTO(
                entity.getUuid(),
                entity.getName(),
                entity.getAcronym(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getAddress(),
                entity.getLogo(),
                entity.getFax(),
                entity.getGsm()
        );
    }
}
