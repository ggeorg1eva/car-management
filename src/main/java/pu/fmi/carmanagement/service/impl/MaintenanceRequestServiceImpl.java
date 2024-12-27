package pu.fmi.carmanagement.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pu.fmi.carmanagement.model.dto.ResponseMaintenanceDTO;
import pu.fmi.carmanagement.model.entity.MaintenanceRequest;
import pu.fmi.carmanagement.repository.MaintenanceRequestRepository;
import pu.fmi.carmanagement.service.MaintenanceRequestService;

import java.util.Optional;

@Service
public class MaintenanceRequestServiceImpl implements MaintenanceRequestService {
    private final MaintenanceRequestRepository requestRepository;

    public MaintenanceRequestServiceImpl(MaintenanceRequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public ResponseMaintenanceDTO getRequestById(Long id) {
        Optional<MaintenanceRequest> request = requestRepository.findById(id);
        if (request.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a maintenance request with id = " + id);
        }

        return null;
    }
}
