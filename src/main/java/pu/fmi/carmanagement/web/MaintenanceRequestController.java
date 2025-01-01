package pu.fmi.carmanagement.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pu.fmi.carmanagement.model.dto.request.CreateMaintenanceDTO;
import pu.fmi.carmanagement.model.dto.request.UpdateMaintenanceDTO;
import pu.fmi.carmanagement.model.dto.response.MonthlyRequestsReportDTO;
import pu.fmi.carmanagement.model.dto.response.ResponseMaintenanceDTO;
import pu.fmi.carmanagement.service.MaintenanceRequestService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/maintenance")
public class MaintenanceRequestController {
    private final MaintenanceRequestService requestService;

    public MaintenanceRequestController(MaintenanceRequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("")
    public ResponseEntity<List<ResponseMaintenanceDTO>> getAllRequests(
            @Positive @RequestParam(value = "carId", required = false) Long carId,
            @Positive @RequestParam(value = "garageId", required = false) Long garageId,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(value = "endDate", required = false) LocalDate endDate
    ) {
        List<ResponseMaintenanceDTO> resp = requestService.getAllRequestsWithFilters(carId, garageId, startDate, endDate);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ResponseMaintenanceDTO> createRequest(@RequestBody @Valid CreateMaintenanceDTO requestDTO) {
        ResponseMaintenanceDTO resp = requestService.createRequest(requestDTO);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMaintenanceDTO> getRequestById(@PathVariable("id") @Positive @NotNull Long id) {
        ResponseMaintenanceDTO resp = requestService.getRequestById(id);
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMaintenanceDTO> updateRequest(@PathVariable @Positive @NotNull Long id, @RequestBody @Valid UpdateMaintenanceDTO requestDTO) {
        ResponseMaintenanceDTO resp = requestService.updateRequest(id, requestDTO);
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteRequest(@PathVariable("id") @Positive @NotNull Long id) {
        Boolean resp = requestService.deleteRequest(id);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("monthlyRequestsReport")
    public ResponseEntity<List<MonthlyRequestsReportDTO>> getMonthlyRequestsReport(
            @Positive @NotNull @RequestParam(value = "garageId") Long garageId,
            @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])$", message = "Invalid format. Expected yyyy-MM.") @RequestParam(value = "startMonth") String startMonth,
            @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])$", message = "Invalid format. Expected yyyy-MM.") @RequestParam(value = "endMonth") String endMonth
    ) {
        List<MonthlyRequestsReportDTO> resp = requestService.getMonthlyRequestsReport(garageId, startMonth, endMonth);
        return ResponseEntity.ok(resp);
    }
}