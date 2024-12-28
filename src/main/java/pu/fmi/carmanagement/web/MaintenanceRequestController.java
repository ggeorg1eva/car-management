package pu.fmi.carmanagement.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pu.fmi.carmanagement.model.dto.response.ResponseMaintenanceDTO;
import pu.fmi.carmanagement.model.dto.request.UpdateMaintenanceDTO;
import pu.fmi.carmanagement.service.MaintenanceRequestService;

@RestController
@RequestMapping("/maintenance")
public class MaintenanceRequestController {
    private final MaintenanceRequestService requestService;

    public MaintenanceRequestController(MaintenanceRequestService requestService) {
        this.requestService = requestService;
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
}