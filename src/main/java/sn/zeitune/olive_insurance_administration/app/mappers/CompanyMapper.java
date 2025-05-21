package sn.zeitune.olive_insurance_administration.app.mappers;

import sn.zeitune.olive_insurance_administration.app.dto.requests.CompanyRequestDTO;
import sn.zeitune.olive_insurance_administration.app.dto.responses.CompanyResponseDTO;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.Company;
import sn.zeitune.olive_insurance_administration.enums.ManagementEntityType;

public class CompanyMapper {

    public static Company map(CompanyRequestDTO dto, Company company) {
        company.setName(dto.name());
        company.setAcronym(dto.acronym());
        company.setEmail(dto.email());
        company.setPhone(dto.phone());
        company.setAddress(dto.address());
        company.setLogo(dto.logo());
        company.setFax(dto.fax());
        company.setGsm(dto.gsm());
        company.setLegalStatus(dto.legalStatus());
        company.setRegistrationNumber(dto.registrationNumber());
        company.setType(ManagementEntityType.COMPANY);
        return company;
    }

    public static CompanyResponseDTO map(Company company) {
        return new CompanyResponseDTO(
                company.getUuid(),
                company.getName(),
                company.getAcronym(),
                company.getEmail(),
                company.getPhone(),
                company.getAddress(),
                company.getLogo(),
                company.getFax(),
                company.getGsm(),
                company.getLegalStatus(),
                company.getRegistrationNumber()
        );
    }
}
