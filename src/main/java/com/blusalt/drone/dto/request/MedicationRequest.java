package com.blusalt.drone.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class MedicationRequest {


        @NotBlank
        @Length(min = 1, max = 400)
        private String name;

        @Positive()
        private Integer weight;

        @NotBlank
        @Length(min = 1, max = 400)
        private String code;

        @NotBlank
        @Length(min = 1, max = 500)
        @URL
        private String imageUrl;



}
