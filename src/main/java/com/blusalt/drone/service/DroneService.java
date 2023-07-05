package com.blusalt.drone.service;

import com.blusalt.drone.dto.request.DroneRequest;
import com.blusalt.drone.dto.response.GenericResponse;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface DroneService {

    ResponseEntity<?>  droneRegistration(DroneRequest droneRequest) throws IOException;

    ResponseEntity<?>  getAvailableDrones() throws IOException;

    ResponseEntity<?> getDroneBySerialNumber(String serialNumber) throws IOException;

    public static GenericResponse internalError() {

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setResponseCode(ResponseStatus.INTERNAL_SERVER_RESP_CODE.getValue());
        genericResponse.setResponseStatus(ResponseStatus.FAILED_STATUS.getValue());
        genericResponse.setResponseMessage(ResponseStatus.INTERNAL_SERVER_RESP_MSG.getValue());

        return genericResponse;
    }


}
