package pu.fmi.carmanagement.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pu.fmi.carmanagement.model.dto.request.UpdateGarageDTO;
import pu.fmi.carmanagement.model.dto.response.ResponseGarageDTO;
import pu.fmi.carmanagement.model.dto.response.ResponseMaintenanceDTO;
import pu.fmi.carmanagement.service.GarageService;

@RestController
@RequestMapping("/garages")
public class GarageController {
    private final GarageService garageService;

    public GarageController(GarageService garageService) {
        this.garageService = garageService;
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

}

