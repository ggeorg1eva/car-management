package pu.fmi.carmanagement.service;

import pu.fmi.carmanagement.model.dto.request.UpdateGarageDTO;
import pu.fmi.carmanagement.model.dto.response.ResponseGarageDTO;

public interface GarageService {
    ResponseGarageDTO getGarageById(Long id);

    ResponseGarageDTO updateGarage(Long id, UpdateGarageDTO garageDTO);

    Boolean deleteGarage(Long id);
}
