package com.blusalt.drone.service.impl;

import com.blusalt.drone.dto.request.DroneRequest;
import com.blusalt.drone.dto.response.AvailableDronesResponse;
import com.blusalt.drone.dto.response.DroneRegistrationResponse;
import com.blusalt.drone.dto.response.DroneResponse;
import com.blusalt.drone.model.Drone;
import com.blusalt.drone.model.enumeration.State;
import com.blusalt.drone.repository.DroneRepository;
import com.blusalt.drone.service.DroneService;
import com.blusalt.drone.service.ResponseStatus;
import com.google.gson.Gson;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class DroneServiceImpl implements DroneService {


    Logger log = LoggerFactory.getLogger(DroneServiceImpl.class);

    @Autowired
    DroneRepository droneRepository;

    @Autowired
    Gson gson;

    @Value("${drone.maximumDrones}")
    public Integer maximumDrones;

    @Override
    public ResponseEntity<?> droneRegistration(DroneRequest droneRequest) throws IOException {

        log.info("Calling drone registration service......");
        ObjectMapper objectMapper = new ObjectMapper();
        Drone drone = new Drone();
        DroneRegistrationResponse droneRegistrationResponse = new DroneRegistrationResponse();
        DroneRegistrationResponse.RegistrationResponse registrationResponse = new DroneRegistrationResponse.RegistrationResponse();

        try {

           Optional<Drone> checkIfSerialNumberExist = droneRepository.findBySerialNumber(droneRequest.getDroneRegistration().getSerialNumber());

           if(checkIfSerialNumberExist.isPresent()) {

               droneRegistrationResponse.setResponseCode(ResponseStatus.SERIAL_NUMBER_RESP2_CODE.getValue());
               droneRegistrationResponse.setResponseStatus(ResponseStatus.FAILED_STATUS.getValue());
               droneRegistrationResponse.setResponseMessage(ResponseStatus.SERIAL_NUMBER_RESP2_MSG.getValue());

               return ResponseEntity.accepted().body(gson.fromJson(objectMapper.writeValueAsString(droneRegistrationResponse),DroneRegistrationResponse.class));
           }

            if(droneRepository.count() >= maximumDrones) {

                droneRegistrationResponse.setResponseCode(ResponseStatus.DRONE_LIMIT_RESP_CODE.getValue());
                droneRegistrationResponse.setResponseStatus(ResponseStatus.FAILED_STATUS.getValue());
                droneRegistrationResponse.setResponseMessage(ResponseStatus.DRONE_LIMIT_RESP_MSG.getValue());

                return ResponseEntity.accepted().body(gson.fromJson(objectMapper.writeValueAsString(droneRegistrationResponse),DroneRegistrationResponse.class));
            }


            drone.setSerialNumber(droneRequest.getDroneRegistration().getSerialNumber());
            drone.setModel(droneRequest.getDroneRegistration().getModel());
            drone.setState(State.LOADING);
            droneRepository.save(drone);

            Optional<Drone> droneDetails = droneRepository.findBySerialNumber(droneRequest.getDroneRegistration().getSerialNumber());
            registrationResponse.setId(droneDetails.get().getId());
            registrationResponse.setSerialNumber(droneDetails.get().getSerialNumber());
            registrationResponse.setWeightLimit(droneDetails.get().getWeightLimit());
            registrationResponse.setBatteryCapacity(droneDetails.get().getBatteryCapacity());
            registrationResponse.setModel(droneDetails.get().getModel().toString());
            registrationResponse.setState(droneDetails.get().getState().toString());
            droneRegistrationResponse.setResponseCode(ResponseStatus.SUCCESS_RESP_CODE.getValue());
            droneRegistrationResponse.setResponseStatus(ResponseStatus.SUCCESS_STATUS.getValue());
            droneRegistrationResponse.setResponseMessage(ResponseStatus.DRONE_SUCCESS_RESP_MSG.getValue());
            droneRegistrationResponse.setRegistrationResponse(registrationResponse);

            return ResponseEntity.ok().body(gson.fromJson(objectMapper.writeValueAsString(droneRegistrationResponse),DroneRegistrationResponse.class));



        } catch(Exception ex) {

            log.info("Drone registration error:  " + ex.fillInStackTrace());

            return ResponseEntity.internalServerError().body(gson.fromJson(objectMapper.writeValueAsString(DroneService.internalError()),DroneRegistrationResponse.class));


        }

    }

    @Override
    public ResponseEntity<?> getAvailableDrones() throws IOException {

        log.info("Calling get available drones service......");
        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject jsonObject = new JSONObject();

        try {

            List<Drone> list = droneRepository.findAllByState(State.LOADING.toString());
            log.info("list: " + list);

            if(list.isEmpty()) {

                jsonObject.put("responseCode",ResponseStatus.DRONE_NOT_AVAILABLE_RESP_CODE.getValue());
                jsonObject.put("responseStatus",ResponseStatus.FAILED_STATUS.getValue());
                jsonObject.put("responseMessage",ResponseStatus.DRONE_NOT_AVAILABLE_RESP_MSG.getValue());
                jsonObject.put("data",(Object)null);

                return ResponseEntity.accepted().body(gson.fromJson(jsonObject.toString(), AvailableDronesResponse.class));

            }

            jsonObject.put("responseCode",ResponseStatus.SUCCESS_RESP_CODE.getValue());
            jsonObject.put("responseStatus",ResponseStatus.SUCCESS_STATUS.getValue());
            jsonObject.put("responseMessage",ResponseStatus.SUCCESS_MSG.getValue());
            jsonObject.put("data",list);

            return ResponseEntity.ok().body(gson.fromJson(jsonObject.toString(),AvailableDronesResponse.class));


        } catch(Exception ex) {

            log.info("Available drones error:  " + ex.fillInStackTrace());

            return ResponseEntity.internalServerError().body(gson.fromJson(objectMapper.writeValueAsString(DroneService.internalError()),DroneRegistrationResponse.class));


        }

    }

    @Override
    public ResponseEntity<?> getDroneBySerialNumber(String serialNumber) throws IOException {

        log.info("Calling get drone by serial number......");
        ObjectMapper objectMapper = new ObjectMapper();
        DroneResponse droneResponse = new DroneResponse();
        DroneResponse.DataDetails data = new DroneResponse.DataDetails();

        try {

            Optional<Drone> checkIfSerialNumberExist = droneRepository.findBySerialNumber(serialNumber);

            if(!checkIfSerialNumberExist.isPresent()) {

                droneResponse.setResponseCode(ResponseStatus.SERIAL_NUMBER_RESP_CODE.getValue());
                droneResponse.setResponseStatus(ResponseStatus.FAILED_STATUS.getValue());
                droneResponse.setResponseMessage(ResponseStatus.SERIAL_NUMBER_RESP_MSG.getValue());

                return ResponseEntity.accepted().body(gson.fromJson(objectMapper.writeValueAsString(droneResponse), DroneResponse.class));
            }

            droneResponse.setResponseCode(ResponseStatus.SUCCESS_RESP_CODE.getValue());
            droneResponse.setResponseStatus(ResponseStatus.SUCCESS_STATUS.getValue());
            droneResponse.setResponseMessage(ResponseStatus.SUCCESS_MSG.getValue());
            data.setId(checkIfSerialNumberExist.get().getId());
            data.setSerialNumber(checkIfSerialNumberExist.get().getSerialNumber());
            data.setBatteryCapacity(checkIfSerialNumberExist.get().getBatteryCapacity());
            data.setWeightLimit(checkIfSerialNumberExist.get().getWeightLimit());
            data.setModel(checkIfSerialNumberExist.get().getModel().toString());
            data.setState(checkIfSerialNumberExist.get().getState().toString());
            droneResponse.setData(data);

            return ResponseEntity.ok().body(gson.fromJson(objectMapper.writeValueAsString(droneResponse), DroneResponse.class));


        } catch (Exception ex) {

            log.info("Drone by serial number error:  " + ex.fillInStackTrace());

            return ResponseEntity.internalServerError().body(gson.fromJson(objectMapper.writeValueAsString(DroneService.internalError()),DroneRegistrationResponse.class));


        }
    }




}
