package pu.fmi.carmanagement.service;

import pu.fmi.carmanagement.model.dto.request.CreateMaintenanceDTO;
import pu.fmi.carmanagement.model.dto.response.MonthlyRequestsReportDTO;
import pu.fmi.carmanagement.model.dto.response.ResponseMaintenanceDTO;
import pu.fmi.carmanagement.model.dto.request.UpdateMaintenanceDTO;

import java.time.LocalDate;
import java.util.List;

public interface MaintenanceRequestService {
    ResponseMaintenanceDTO getRequestById(Long id);

    ResponseMaintenanceDTO updateRequest(Long id, UpdateMaintenanceDTO requestDTO);

    ResponseMaintenanceDTO deleteRequest(Long id);
    List<ResponseMaintenanceDTO> getAllRequestsWithFilters(Long carId, Long garageId, LocalDate startDate, LocalDate endDate);

    ResponseMaintenanceDTO createRequest(CreateMaintenanceDTO requestDTO);

    List<MonthlyRequestsReportDTO> getMonthlyRequestsReport(Long garageId, String startMonth, String endMonth);
}
