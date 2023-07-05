package com.blusalt.drone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDronesResponse  extends GenericResponse{

    private List<DataDetails> data;

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
