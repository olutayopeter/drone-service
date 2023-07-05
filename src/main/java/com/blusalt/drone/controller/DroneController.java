package com.blusalt.drone.controller;


import com.blusalt.drone.dto.request.DroneRequest;
import com.blusalt.drone.dto.request.MedicationRequest;
import com.blusalt.drone.dto.response.DroneMedicationResponse;
import com.blusalt.drone.dto.response.DroneRegistrationResponse;
import com.blusalt.drone.dto.response.GenericResponse;
import com.blusalt.drone.service.DroneService;
import com.blusalt.drone.service.MedicationService;
import com.blusalt.drone.service.ResponseStatus;
import com.blusalt.drone.util.AppUtils;
import com.google.gson.Gson;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class DroneController {

    Logger log = LoggerFactory.getLogger(DroneController.class);

    @Autowired
    DroneService droneService;

    @Autowired
    MedicationService medicationService;

    @Autowired
    Gson gson;

    @PostMapping(path= "/drone-registration",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> droneRegistration(@Valid @RequestBody DroneRequest droneRequest) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        DroneRegistrationResponse droneRegistrationResponse = new DroneRegistrationResponse();

        if(droneRequest.getDroneRegistration() == null) {

            droneRegistrationResponse.setResponseCode(ResponseStatus.FIELD_MISSING_RESP_CODE.getValue());
            droneRegistrationResponse.setResponseStatus(ResponseStatus.FAILED_STATUS.getValue());
            droneRegistrationResponse.setResponseMessage(ResponseStatus.FIELD_MISSING_RESP_MSG.getValue());

//            jsonObject.put("responseCode", ResponseStatus.FIELD_MISSING_RESP_CODE.getValue());
//            jsonObject.put("responseStatus", ResponseStatus.FAILED_STATUS.getValue());
//            jsonObject.put("responseMessage", ResponseStatus.FIELD_MISSING_RESP_MSG.getValue());
//            jsonObject.put("registrationResponse",(Object) null);

            return ResponseEntity.accepted().body(gson.fromJson(objectMapper.writeValueAsString(droneRegistrationResponse),DroneRegistrationResponse.class));

        }

        if(droneRequest.getDroneRegistration().getSerialNumber() == null || droneRequest.getDroneRegistration().getSerialNumber().isEmpty() ||
           droneRequest.getDroneRegistration().getModel() == null) {

            droneRegistrationResponse.setResponseCode(ResponseStatus.FIELD_MISSING_RESP_CODE.getValue());
            droneRegistrationResponse.setResponseStatus(ResponseStatus.FAILED_STATUS.getValue());
            droneRegistrationResponse.setResponseMessage(ResponseStatus.FIELD_MISSING_RESP_MSG.getValue());

            return ResponseEntity.accepted().body(gson.fromJson(objectMapper.writeValueAsString(droneRegistrationResponse),DroneRegistrationResponse.class));

        }

        if(droneRequest.getDroneRegistration().getSerialNumber().length() > 100) {

            droneRegistrationResponse.setResponseCode(ResponseStatus.CHARACTER_LENGTH_RESP_CODE.getValue());
            droneRegistrationResponse.setResponseStatus(ResponseStatus.FAILED_STATUS.getValue());
            droneRegistrationResponse.setResponseMessage(ResponseStatus.CHARACTER_LENGTTH_RESP_MSG.getValue());

            return ResponseEntity.accepted().body(gson.fromJson(objectMapper.writeValueAsString(droneRegistrationResponse),DroneRegistrationResponse.class));

        }

        try {

            return droneService.droneRegistration(droneRequest);

        } catch(Exception ex) {

            log.info("drone-registration error: " + ex.fillInStackTrace());

            return ResponseEntity.internalServerError().body(gson.fromJson(objectMapper.writeValueAsString(DroneService.internalError()), DroneRegistrationResponse.class));


        }

    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> availableDrones() throws IOException {

        return droneService.getAvailableDrones();

    }

    @GetMapping(path = "/{serialNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> drone(@PathVariable @Valid String serialNumber) throws IOException {

        return droneService.getDroneBySerialNumber(serialNumber);

    }

    @PostMapping(path = "/medications/{serialNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addMedications(@PathVariable("serialNumber") String serialNumber, @RequestBody List<MedicationRequest> medicationRequestList) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        GenericResponse genericResponse = new GenericResponse();

        if(serialNumber.isEmpty() || medicationRequestList.isEmpty()) {

            genericResponse.setResponseCode(ResponseStatus.FIELD_MISSING_RESP_CODE.getValue());
            genericResponse.setResponseStatus(ResponseStatus.FAILED_STATUS.getValue());
            genericResponse.setResponseMessage(ResponseStatus.FIELD_MISSING_RESP_MSG.getValue());

            return ResponseEntity.accepted().body(gson.fromJson(objectMapper.writeValueAsString(genericResponse), GenericResponse.class));

        }

        for (int i = 0; i < medicationRequestList.size(); i++) {

            if(!AppUtils.isAllowedUpperLettersUnderscoreAndNumbers(medicationRequestList.get(i).getCode())) {

                genericResponse.setResponseCode(ResponseStatus.MEDICATION_CODE_RESP_CODE.getValue());
                genericResponse.setResponseStatus(ResponseStatus.FAILED_STATUS.getValue());
                genericResponse.setResponseMessage(ResponseStatus.MEDICATION_CODE_RESP_MSG.getValue());

                return ResponseEntity.accepted().body(gson.fromJson(objectMapper.writeValueAsString(genericResponse), GenericResponse.class));


            }

            if(!AppUtils.isAllowedLettersNumbersHypenAndUnderscore(medicationRequestList.get(i).getName())) {

                genericResponse.setResponseCode(ResponseStatus.MEDICATION_NAME_RESP_CODE.getValue());
                genericResponse.setResponseStatus(ResponseStatus.FAILED_STATUS.getValue());
                genericResponse.setResponseMessage(ResponseStatus.MEDICATION_NAME_RESP_MSG.getValue());

                return ResponseEntity.accepted().body(gson.fromJson(objectMapper.writeValueAsString(genericResponse), GenericResponse.class));


            }

        }


        try {

            return medicationService.addMedication(medicationRequestList,serialNumber);

        } catch(Exception ex) {


            log.info("drone-registration error: " + ex.fillInStackTrace());

            return ResponseEntity.internalServerError().body(gson.fromJson(objectMapper.writeValueAsString(DroneService.internalError()), GenericResponse.class));


        }
    }


    @GetMapping(path = "/medications/{serialNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMedications(@PathVariable @Valid String serialNumber) throws IOException {

        return medicationService.getMedications(serialNumber);

    }







}
