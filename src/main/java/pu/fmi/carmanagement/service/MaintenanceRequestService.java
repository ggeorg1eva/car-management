package pu.fmi.carmanagement.service;

import pu.fmi.carmanagement.model.dto.response.ResponseMaintenanceDTO;
import pu.fmi.carmanagement.model.dto.request.UpdateMaintenanceDTO;

public interface MaintenanceRequestService {
    ResponseMaintenanceDTO getRequestById(Long id);

    ResponseMaintenanceDTO updateRequest(Long id, UpdateMaintenanceDTO requestDTO);
}
