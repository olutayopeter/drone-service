package com.blusalt.drone.dto.request;

import com.blusalt.drone.model.enumeration.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class DroneRequest {


    private Request droneRegistration;

    @Data
    public static class Request {

        private String serialNumber;

        private Model model;
    }

}
