package sn.zeitune.oliveinsuranceback.app.enums;

import lombok.Getter;

@Getter
public enum PointOfSaleType {
    GENERAL_AGENT(true),
    DIRECT_OFFICE(true),
    BROKER(false);
    private final boolean linked;
    PointOfSaleType(boolean linked) {
        this.linked = linked;
    }
}
