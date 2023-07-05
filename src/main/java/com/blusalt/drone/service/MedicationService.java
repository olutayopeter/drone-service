package com.blusalt.drone.service;

import com.blusalt.drone.dto.request.MedicationRequest;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface MedicationService {

    ResponseEntity<?>  addMedication(List<MedicationRequest> medicationRequest, String serialNumber) throws IOException;
    ResponseEntity<?> getMedications(String serialNumber) throws IOException;
}
