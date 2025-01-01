package pu.fmi.carmanagement.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pu.fmi.carmanagement.model.dto.request.CreateCarDTO;
import pu.fmi.carmanagement.model.dto.request.UpdateCarDTO;
import pu.fmi.carmanagement.model.dto.response.ResponseCarDTO;
import pu.fmi.carmanagement.service.CarService;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("")
    public ResponseEntity<List<ResponseCarDTO>> getAllCars(
            @Size(max = 30) @RequestParam(value = "carMake", required = false) String carMake,
            @Positive @RequestParam(value = "garageId", required = false) Long garageId,
            @Positive @RequestParam(value = "fromYear", required = false) Integer fromYear,
            @Positive @RequestParam(value = "toYear", required = false) Integer toYear
    ) {
        List<ResponseCarDTO> resp = carService.getAllCarsWithFilters(carMake, garageId, fromYear, toYear);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ResponseCarDTO> createCar(@RequestBody @Valid CreateCarDTO carDTO) {
        ResponseCarDTO resp = carService.createCar(carDTO);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCarDTO> getCarById(@PathVariable("id") @Positive @NotNull Long id) {
        ResponseCarDTO resp = carService.getCarById(id);
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseCarDTO> updateCar(@PathVariable @Positive @NotNull Long id, @RequestBody @Valid UpdateCarDTO carDTO) {
        ResponseCarDTO resp = carService.updateCar(id, carDTO);
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCar(@PathVariable("id") @Positive @NotNull Long id) {
        Boolean resp = carService.deleteCar(id);
        return ResponseEntity.ok(resp);
    }
}
