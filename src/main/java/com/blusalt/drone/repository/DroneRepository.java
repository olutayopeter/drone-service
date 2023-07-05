package com.blusalt.drone.repository;

import com.blusalt.drone.model.Drone;
import com.blusalt.drone.model.enumeration.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository<Drone,Long> {

    @Query(value = "SELECT * FROM TBL_DRONES WHERE STATE = :state",nativeQuery = true)
    List<Drone> findAllByState(String state);

    @Query(value = "SELECT * FROM TBL_DRONES WHERE SERIAL_NUMBER = :serialNumber",nativeQuery = true)
    Optional<Drone> findBySerialNumber(String serialNumber);

    @Query(value = "SELECT * FROM TBL_DRONES WHERE SERIAL_NUMBER = :serialNumber",nativeQuery = true)
    Drone findDroneBySerialNumber(String serialNumber);


}
