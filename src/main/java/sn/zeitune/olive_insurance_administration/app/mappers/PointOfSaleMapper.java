package sn.zeitune.olive_insurance_administration.app.mappers;

import sn.zeitune.olive_insurance_administration.app.dto.responses.PointOfSaleResponseDTO;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale.BrokerPointOfSale;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale.CompanyPointOfSale;
import sn.zeitune.olive_insurance_administration.app.entities.managemententity.pointofsale.PointOfSale;

public class PointOfSaleMapper {

    public static PointOfSaleResponseDTO map(BrokerPointOfSale pdv) {
        return new PointOfSaleResponseDTO(
                pdv.getUuid(),
                pdv.getName(),
                pdv.getEmail(),
                pdv.getPhone(),
                pdv.getAddress(),
                pdv.getTypePointOfSale(),
                "Courtier"
        );
    }

    public static PointOfSaleResponseDTO map(CompanyPointOfSale pdv) {
        return new PointOfSaleResponseDTO(
                pdv.getUuid(),
                pdv.getName(),
                pdv.getEmail(),
                pdv.getPhone(),
                pdv.getAddress(),
                pdv.getTypePointOfSale(),
                pdv.getCompany().getName()
        );
    }

    public static PointOfSaleResponseDTO map(PointOfSale pointOfSale) {
        if (pointOfSale instanceof BrokerPointOfSale brokerPointOfSale) {
            return map(brokerPointOfSale);
        } else if (pointOfSale instanceof CompanyPointOfSale companyPointOfSale) {
            return map(companyPointOfSale);
        } else {
            throw new IllegalArgumentException("Unknown PointOfSale type: " + pointOfSale.getClass());
        }
    }
}
