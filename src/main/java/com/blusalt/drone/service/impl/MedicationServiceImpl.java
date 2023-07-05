package com.blusalt.drone.service.impl;


import com.blusalt.drone.dto.request.MedicationRequest;
import com.blusalt.drone.dto.response.DroneMedicationResponse;
import com.blusalt.drone.dto.response.DroneRegistrationResponse;
import com.blusalt.drone.dto.response.GenericResponse;
import com.blusalt.drone.model.Drone;
import com.blusalt.drone.model.Medication;
import com.blusalt.drone.model.enumeration.State;
import com.blusalt.drone.repository.DroneRepository;
import com.blusalt.drone.repository.MedicationRepository;
import com.blusalt.drone.service.DroneService;
import com.blusalt.drone.service.MedicationService;
import com.blusalt.drone.service.ResponseStatus;
import com.google.gson.Gson;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MedicationServiceImpl implements MedicationService {

    Logger log = LoggerFactory.getLogger(MedicationServiceImpl.class);

    @PersistenceContext
    @Autowired
    EntityManager entityManager;

    @Autowired
    MedicationRepository medicationRepository;

    @Autowired
    DroneRepository droneRepository;

    @Autowired
    Gson gson;

    @Value("${drone.minimumBatteryCapacity}")
    public Integer minimumBatteryCapacity;


    @Override
    public ResponseEntity<?> addMedication(List<MedicationRequest> medicationRequests, String serialNumber) throws IOException {

        log.info("Calling add medication service......");
        ObjectMapper objectMapper = new ObjectMapper();
        Integer totalWeight = 0;
        Medication medication = new Medication();
        DroneMedicationResponse droneMedicationResponse = new DroneMedicationResponse();

        try {

            Drone drone = droneRepository.findDroneBySerialNumber(serialNumber);

            if(drone == null) {

                droneMedicationResponse.setResponseCode(ResponseStatus.SERIAL_NUMBER_RESP_CODE.getValue());
                droneMedicationResponse.setResponseStatus(ResponseStatus.FAILED_STATUS.getValue());
                droneMedicationResponse.setResponseMessage(ResponseStatus.SERIAL_NUMBER_RESP_MSG.getValue());

                return ResponseEntity.accepted().body(gson.fromJson(objectMapper.writeValueAsString(droneMedicationResponse), GenericResponse.class));
            }

            if (drone.getState() != State.LOADING) {

                droneMedicationResponse.setResponseCode(ResponseStatus.DRONE_NOT_IN_LOADING_RESP_CODE.getValue());
                droneMedicationResponse.setResponseStatus(ResponseStatus.FAILED_STATUS.getValue());
                droneMedicationResponse.setResponseMessage(ResponseStatus.DRONE_NOT_IN_LOADING_RESP_MSG.getValue());

                return ResponseEntity.accepted().body(gson.fromJson(objectMapper.writeValueAsString(droneMedicationResponse), GenericResponse.class));
            }

            if (drone.getBatteryCapacity() <  minimumBatteryCapacity) {

                droneMedicationResponse.setResponseCode(ResponseStatus.LOW_BATTERY_RESP_CODE.getValue());
                droneMedicationResponse.setResponseStatus(ResponseStatus.FAILED_STATUS.getValue());
                droneMedicationResponse.setResponseMessage(ResponseStatus.LOW_BATTERY_RESP_MSG.getValue());

                return ResponseEntity.accepted().body(gson.fromJson(objectMapper.writeValueAsString(droneMedicationResponse), GenericResponse.class));
            }

            List<Medication>  medications = null;
            List<Medication> medicationList = new ArrayList<>(medicationRequests.size());
            for (MedicationRequest medicationRequest : medicationRequests) {
                Integer weight = medicationRequest.getWeight();
                totalWeight += weight;
                medication.setCode(medicationRequest.getCode());
                medication.setImageUrl(medicationRequest.getImageUrl());
                medication.setWeight(weight);
                medication.setName(medicationRequest.getName());
                medication.setDrone(drone);

                medicationList.add(medication);

                drone.setState(State.LOADED);
                entityManager.persist(drone);
                medications = medicationRepository.saveAll(medicationList);
            }

            if (drone.getWeightLimit() < totalWeight) {

                droneMedicationResponse.setResponseCode(ResponseStatus.MEDICATION_WEIGHT_RESP_CODE.getValue());
                droneMedicationResponse.setResponseStatus(ResponseStatus.FAILED_STATUS.getValue());
                droneMedicationResponse.setResponseMessage(ResponseStatus.MEDICATION_WEIGHT_RESP_MSG.getValue());

                return ResponseEntity.accepted().body(gson.fromJson(objectMapper.writeValueAsString(droneMedicationResponse), GenericResponse.class));
            }

//            drone.setState(State.LOADED);
//            entityManager.persist(drone);
//            List<Medication>  medications = medicationRepository.saveAll(medicationList);

            if(!medications.isEmpty() || medications.size() > 0) {

                droneMedicationResponse.setResponseCode(ResponseStatus.SUCCESS_RESP_CODE.getValue());
                droneMedicationResponse.setResponseStatus(ResponseStatus.SUCCESS_STATUS.getValue());
                droneMedicationResponse.setResponseMessage(ResponseStatus.SUCCESS_MSG.getValue());

                return ResponseEntity.ok().body(gson.fromJson(objectMapper.writeValueAsString(droneMedicationResponse), GenericResponse.class));

            } else {

                droneMedicationResponse.setResponseCode(ResponseStatus.PROCESSING_ERROR_RESP_CODE.getValue());
                droneMedicationResponse.setResponseStatus(ResponseStatus.PROCESSING_ERROR_RESP_CODE.getValue());
                droneMedicationResponse.setResponseMessage(ResponseStatus.PROCESSING_ERROR_RESP_MSG.getValue());

                return ResponseEntity.ok().body(gson.fromJson(objectMapper.writeValueAsString(droneMedicationResponse), GenericResponse.class));


            }


        } catch(Exception ex) {

            log.info("Add medication error:  " + ex.fillInStackTrace());

            return ResponseEntity.internalServerError().body(gson.fromJson(objectMapper.writeValueAsString(DroneService.internalError()), DroneRegistrationResponse.class));


        }

    }

    @Override
    public  ResponseEntity<?> getMedications(String serialNumber) throws IOException {

        log.info("Calling get medication service......");
        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject jsonObject = new JSONObject();
        DroneMedicationResponse droneMedicationResponse = new DroneMedicationResponse();
        DroneMedicationResponse.DataDetails dataDetails = new DroneMedicationResponse.DataDetails();

        try {

            List<Medication> medications = medicationRepository.findMedicationBySerialNumber(serialNumber);
            log.info("medications:  " + medications);
            if(medications.isEmpty()) {


                jsonObject.put("responseCode",ResponseStatus.MEDICATION_NOT_AVAILABLE_RESP_CODE.getValue());
                jsonObject.put("responseStatus",ResponseStatus.FAILED_STATUS.getValue());
                jsonObject.put("responseMessage",ResponseStatus.MEDICATION_NOT_AVAILABLE_RESP_MSG.getValue());

                return ResponseEntity.accepted().body(gson.fromJson(objectMapper.writeValueAsString(droneMedicationResponse), DroneMedicationResponse.class));

            }


            jsonObject.put("responseCode",ResponseStatus.SUCCESS_RESP_CODE.getValue());
            jsonObject.put("responseStatus",ResponseStatus.SUCCESS_STATUS.getValue());
            jsonObject.put("responseMessage",ResponseStatus.SUCCESS_MSG.getValue());
            jsonObject.put("data",medications);

            return ResponseEntity.ok().body(gson.fromJson(jsonObject.toString(), DroneMedicationResponse.class));


        } catch(Exception ex) {

            log.info("Get medication error:  " + ex.fillInStackTrace());

            return ResponseEntity.internalServerError().body(gson.fromJson(objectMapper.writeValueAsString(DroneService.internalError()),DroneRegistrationResponse.class));


        }

    }
}
