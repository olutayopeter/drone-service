package com.blusalt.drone.repository;

import com.blusalt.drone.model.Drone;
import com.blusalt.drone.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicationRepository extends JpaRepository<Medication,Long> {


    @Query("SELECT u FROM Medication u JOIN u.drone r WHERE r.serialNumber = :serialNumber")
    List<Medication> findMedicationBySerialNumber(String serialNumber);

    @Modifying
    @Transactional
    @Query("UPDATE Medication m SET m.isDelivered = true WHERE m.drone IN (:drones)")
    Integer updateMedication(List<Drone> drones);


}
