package com.blusalt.drone.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TBL_MEDICATION")
public class Medication {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Length(min = 1, max = 500)
    @Getter
    @Setter
    @Column(name = "NAME",nullable = false)
    private String name;

    @NotNull
    @Max(500)
    @Min(1)
    @Getter
    @Setter
    @Column(name = "WEIGHT",nullable = false)
    private Integer weight;

    @NotBlank
    @Length(min = 1, max = 500)
    @Getter
    @Setter
    @Column(name = "CODE",nullable = false)
    private String code;

    @NotBlank
    @Length(min = 1, max = 500)
    @Getter
    @Setter
    @Column(name = "IMAGE_URL",nullable = false)
    private String imageUrl;


    @NotNull
    @Getter
    @Setter
    @Column(name = "IS_DELIVERED",nullable = false)
    private Boolean isDelivered = false;

    @Setter
    @Getter
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "DRONE_SERIAL_NUMBER",referencedColumnName = "SERIAL_NUMBER")
    @JsonBackReference
    private Drone drone;

    @Getter
    @Basic
    @CreationTimestamp
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "ENTERED_DATE")
    private java.time.LocalDateTime enteredDate;

    @Getter
    @Basic
    @UpdateTimestamp
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "MODIFIED_DATE")
    private java.time.LocalDateTime modifiedDate;


}
