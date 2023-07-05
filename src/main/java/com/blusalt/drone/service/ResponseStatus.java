package com.blusalt.drone.service;

import lombok.Getter;


public enum ResponseStatus {
    SERIAL_NUMBER_RESP_MSG("Serial number does not exist."),
    SERIAL_NUMBER_RESP_CODE("90"),

    SERIAL_NUMBER_RESP2_MSG("Serial number exist."),
    SERIAL_NUMBER_RESP2_CODE("80"),
    DRONE_LIMIT_RESP_MSG("Maximum Drone Limit Reached."),
    DRONE_LIMIT_RESP_CODE("91"),
    DRONE_SUCCESS_RESP_MSG("Drone successfully added."),
    DRONE_NOT_AVAILABLE_RESP_CODE("92"),
    DRONE_NOT_AVAILABLE_RESP_MSG("Drone is not available."),
    DRONE_NOT_IN_LOADING_RESP_MSG("Drone is not in loading state."),
    DRONE_NOT_IN_LOADING_RESP_CODE("93"),
    LOW_BATTERY_RESP_MSG("Drone has low battery capacity."),
    LOW_BATTERY_RESP_CODE("94"),
    MEDICATION_WEIGHT_RESP_CODE("95"),
    MEDICATION_WEIGHT_RESP_MSG("Medication weight is greater than the weight capacity."),

    PROCESSING_ERROR_RESP_CODE("96"),
    PROCESSING_ERROR_RESP_MSG("Error occur during processing."),

    MEDICATION_NOT_AVAILABLE_RESP_CODE("97"),
    MEDICATION_NOT_AVAILABLE_RESP_MSG("Medication is not available."),

    MEDICATION_CODE_RESP_MSG("Invalid code"),
    MEDICATION_CODE_RESP_CODE("88"),
    MEDICATION_NAME_RESP_MSG("Invalid name"),
    MEDICATION_NAME_RESP_CODE("87"),

    //General response
    FAILED_STATUS("failed"),
    SUCCESS_STATUS("success"),
    SUCCESS_MSG("Successful."),
    SUCCESS_RESP_CODE("00"),
    INTERNAL_SERVER_RESP_CODE("99"),
    INTERNAL_SERVER_RESP_MSG("Internal server error."),
    FIELD_MISSING_RESP_MSG("Required parameters are missing"),
    FIELD_MISSING_RESP_CODE("98"),
    CHARACTER_LENGTH_RESP_CODE("89"),
    CHARACTER_LENGTTH_RESP_MSG("Character length cannot be greater than 100");



    private final String value;

    ResponseStatus(String value) {

        this.value = value;
    }

    public String getValue() {

        return value;
    }




}
