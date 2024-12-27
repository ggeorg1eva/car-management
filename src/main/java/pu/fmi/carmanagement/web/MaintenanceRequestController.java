package pu.fmi.carmanagement.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pu.fmi.carmanagement.model.dto.ResponseMaintenanceDTO;
import pu.fmi.carmanagement.service.MaintenanceRequestService;

@RestController
@RequestMapping("/maintenance")
public class MaintenanceRequestController {
    private final MaintenanceRequestService requestService;

    public MaintenanceRequestController(MaintenanceRequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMaintenanceDTO> getRequestById(@PathVariable("id") Long id) {
        ResponseMaintenanceDTO resp = requestService.getRequestById(id);
        return ResponseEntity.ok(resp);
    }
}
//id integer($int64)
//carId integer($int64)
//carName string
//serviceType string
//scheduledDate string($date)
//garageId integer($int64)
//garageName string