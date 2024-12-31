package pu.fmi.carmanagement.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pu.fmi.carmanagement.model.dto.request.CreateGarageDTO;
import pu.fmi.carmanagement.model.dto.request.UpdateGarageDTO;
import pu.fmi.carmanagement.model.dto.response.GarageDailyAvailabilityReportDTO;
import pu.fmi.carmanagement.model.dto.response.ResponseGarageDTO;
import pu.fmi.carmanagement.model.dto.response.ResponseMaintenanceDTO;
import pu.fmi.carmanagement.service.GarageService;

import java.util.List;

@RestController
@RequestMapping("/garages")
public class GarageController {
    private final GarageService garageService;

    public GarageController(GarageService garageService) {
        this.garageService = garageService;
    }

    @GetMapping("")
    public ResponseEntity<List<ResponseGarageDTO>> getAllRequests(@RequestParam(value = "city", required = false) String city) {
        List<ResponseGarageDTO> resp = garageService.getAllGaragesByCity(city);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ResponseGarageDTO> createGarage(@RequestBody @Valid CreateGarageDTO garageDTO) {
        ResponseGarageDTO resp = garageService.createGarage(garageDTO);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseGarageDTO> getGarageById(@PathVariable("id") @Positive @NotNull Long id) {
        ResponseGarageDTO resp = garageService.getGarageById(id);
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseGarageDTO> updateGarage(@PathVariable @Positive @NotNull Long id, @RequestBody @Valid UpdateGarageDTO garageDTO) {
        ResponseGarageDTO resp = garageService.updateGarage(id, garageDTO);
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteRequest(@PathVariable("id") @Positive @NotNull Long id) {
        Boolean resp = garageService.deleteGarage(id);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/dailyAvailabilityReport")
    public ResponseEntity<List<GarageDailyAvailabilityReportDTO>> getDailyAvailabilityReport(
            @Positive @NotNull @RequestParam(value = "garageId") Long garageId,
            @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$", message = "Invalid format. Expected yyyy-MM-dd.") @NotBlank @RequestParam(value = "startDate") String startDate,
            @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$", message = "Invalid format. Expected yyyy-MM-dd.") @NotBlank @RequestParam(value = "endDate") String endDate
    ) {
        List<GarageDailyAvailabilityReportDTO> resp = garageService.getDailyAvailabilityReport(garageId, startDate, endDate);
        return ResponseEntity.ok(resp);
    }

}

