package pu.fmi.carmanagement.service;

import pu.fmi.carmanagement.model.dto.request.CreateCarDTO;
import pu.fmi.carmanagement.model.dto.request.UpdateCarDTO;
import pu.fmi.carmanagement.model.dto.response.ResponseCarDTO;

import java.util.List;

public interface CarService {
    ResponseCarDTO getCarById(Long id);

    ResponseCarDTO updateCar(Long id, UpdateCarDTO carDTO);

    Boolean deleteCar(Long id);

    List<ResponseCarDTO> getAllCarsWithFilters(String carMake, Long garageId, Integer fromYear, Integer toYear);

    ResponseCarDTO createCar(CreateCarDTO carDTO);
}
