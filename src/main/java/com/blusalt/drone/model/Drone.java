package com.blusalt.drone.model;

import com.blusalt.drone.model.enumeration.Model;
import com.blusalt.drone.model.enumeration.State;
import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import org.hibernate.annotations.UpdateTimestamp;

//@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Entity
@Table(name = "TBL_DRONES")
public class Drone implements Serializable {

    @Id
    @Getter
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    public Drone(Long id) {
        this.id = id;
    }



    @NotBlank
    @Length(min = 1, max = 100)
    @Getter
    @Setter
    @Column(name = "SERIAL_NUMBER",nullable = false, unique = true)
    private String serialNumber;


    @NotNull
    @Getter
    @Max(500)
    @Min(1)
    @Column(name = "WEIGHT_LIMIT",nullable = false)
    private Integer weightLimit;

    @NotNull
    @Max(100)
    @Min(0)
    @Getter
    @Setter
    @Column(name = "BATTERY_CAPACITY",nullable = false)
    private Integer batteryCapacity = 100;

    @NotNull
    @Getter
    @Column(name = "MODEL",nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Model model;

    public void setModel(Model model) {
        this.model = model;
        weightLimit = model.weightLimit;
    }

    @NotNull
    @Getter
    @Column(name = "STATE",nullable = false)
    @Enumerated(EnumType.STRING)
    private State state = State.IDLE;

    public void setState(State state) {
        this.state = state;
    }


    @Getter
    @Setter
    @OneToMany(mappedBy = "drone",orphanRemoval = true)
    @JsonManagedReference
    private Collection<Medication> medications = new ArrayList<>();

    @Getter
    @Basic
    @CreationTimestamp
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "ENTERED_DATE")
    private LocalDateTime enteredDate;


    @Getter
    @Basic
    @UpdateTimestamp
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "MODIFIED_DATE")
    private LocalDateTime modifiedDate;

    public Drone(LocalDateTime enteredDate,LocalDateTime modifiedDate) {
        this.enteredDate = enteredDate;
        this.modifiedDate = modifiedDate;
    }


}
