package com.blusalt.drone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GenericResponse {

    private String responseCode;

    private String responseStatus;

    private String responseMessage;
}
