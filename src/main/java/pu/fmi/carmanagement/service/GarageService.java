package pu.fmi.carmanagement.service;

import pu.fmi.carmanagement.model.dto.request.CreateGarageDTO;
import pu.fmi.carmanagement.model.dto.request.UpdateGarageDTO;
import pu.fmi.carmanagement.model.dto.response.GarageDailyAvailabilityReportDTO;
import pu.fmi.carmanagement.model.dto.response.ResponseGarageDTO;

import java.util.List;

public interface GarageService {
    ResponseGarageDTO getGarageById(Long id);

    ResponseGarageDTO updateGarage(Long id, UpdateGarageDTO garageDTO);

    Boolean deleteGarage(Long id);

    ResponseGarageDTO createGarage(CreateGarageDTO garageDTO);

    List<ResponseGarageDTO> getAllGaragesByCity(String city);

    List<GarageDailyAvailabilityReportDTO> getDailyAvailabilityReport(Long garageId, String startDate, String endDate);
}
