package pu.fmi.carmanagement.service;

import pu.fmi.carmanagement.model.dto.ResponseMaintenanceDTO;

public interface MaintenanceRequestService {
    ResponseMaintenanceDTO getRequestById(Long id);
}
