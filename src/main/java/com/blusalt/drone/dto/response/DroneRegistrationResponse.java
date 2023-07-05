package com.blusalt.drone.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DroneRegistrationResponse extends GenericResponse {

    private RegistrationResponse registrationResponse;


    @Data
    public static class RegistrationResponse  {

        private Long id;
        private String serialNumber;
        private Integer weightLimit;
        private Integer batteryCapacity;
        private String model;
        private String state;

    }
}
