package com.blusalt.drone.model.enumeration;

public enum Model {

    LIGHT_WEIGHT(125),
    MIDDLE_WEIGHT(250),
    CRUISER_WEIGHT(375),
    HEAVY_WEIGHT(500);

    public final Integer weightLimit;

    Model(Integer weightLimit) {

        this.weightLimit = weightLimit;
    }


}
