package com.blusalt.drone.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DroneResponse extends GenericResponse {

    DataDetails data;

    @Data
    public static class DataDetails  {

        private Long id;
        private String serialNumber;
        private Integer weightLimit;
        private Integer batteryCapacity;
        private String model;
        private String state;

    }
}
