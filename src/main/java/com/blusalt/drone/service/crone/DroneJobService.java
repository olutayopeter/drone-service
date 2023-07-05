package com.blusalt.drone.service.crone;


import com.blusalt.drone.model.Drone;
import com.blusalt.drone.model.enumeration.State;
import com.blusalt.drone.repository.DroneRepository;
import com.blusalt.drone.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

//@Configuration
//@EnableScheduling
public class DroneJobService {

//    @Autowired
//    DroneRepository droneRepository;
//
//    @Autowired
//    MedicationRepository medicationRepository;
//
//    @Scheduled(fixedDelayString = "${drone.delay}")
//    public void run() {
//        startCharging();
//        checkDroneBattery();
//        startReturning();
//        endDelivery();
//        startDelivery();
//    }
//
//    private List<Drone> transitionState(State previous, State next) {
//        List<Drone> drones = droneRepository.findAllByState(previous);
//        if (drones.size() == 0) return Collections.emptyList();
//        for (Drone drone : drones) {
//            drone.setState(next);
//        }
//        return droneRepository.saveAll(drones);
//    }
//
//    private void startDelivery() {
//        transitionState(State.LOADED, State.DELIVERING);
//    }
//
//    void endDelivery() {
//
//        List<Drone> drones = transitionState(State.DELIVERING, State.DELIVERED);
//
//
//        if (drones.size() == 0) {
//            return;
//        }
//
//        medicationRepository.updateMedication(drones);
//
//
//    }
//
//    private void startReturning() {
//        transitionState(State.DELIVERED, State.RETURNING);
//    }
//
//    private void checkDroneBattery() {
//
//        List<Drone> drones = droneRepository.findAllByState(State.RETURNING);
//        if (drones.size() == 0) {
//            return;
//        }
//        for (Drone drone : drones) {
//            int batteryCapacity = drone.getBatteryCapacity() - 25;
//            drone.setBatteryCapacity(batteryCapacity);
//
//            if (batteryCapacity < 25) {
//                drone.setBatteryCapacity(0);
//                drone.setState(State.IDLE);
//            } else {
//                drone.setState(State.LOADING);
//            }
//
//        }
//
//        droneRepository.saveAll(drones);
//    }
//
//    public void startCharging() {
//
//        List<Drone> drones = droneRepository.findAllByState(State.IDLE);
//        if (drones.size() == 0) {
//            return;
//        }
//        for (Drone drone : drones) {
//            int batteryCapacity = drone.getBatteryCapacity() + 25;
//            drone.setBatteryCapacity(batteryCapacity);
//
//            if (batteryCapacity >= 100) {
//                drone.setBatteryCapacity(100);
//                drone.setState(State.LOADING);
//            } else {
//                drone.setState(State.IDLE);
//            }
//
//        }
//
//        droneRepository.saveAll(drones);
//    }


}
