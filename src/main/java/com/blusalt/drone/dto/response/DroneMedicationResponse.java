package com.blusalt.drone.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DroneMedicationResponse extends GenericResponse {

    private List<DataDetails> data;

    @Data
    public static class DataDetails  {

        private Long id;
        private String name;
        private String weight;
        private String code;
        private String imageUrl;
        private boolean isDelivered;
    }
}
