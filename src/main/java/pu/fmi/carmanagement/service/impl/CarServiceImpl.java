package pu.fmi.carmanagement.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pu.fmi.carmanagement.model.dto.request.CreateCarDTO;
import pu.fmi.carmanagement.model.dto.request.UpdateCarDTO;
import pu.fmi.carmanagement.model.dto.response.ResponseCarDTO;
import pu.fmi.carmanagement.model.entity.Car;
import pu.fmi.carmanagement.model.entity.Garage;
import pu.fmi.carmanagement.model.entity.MaintenanceRequest;
import pu.fmi.carmanagement.repository.CarRepository;
import pu.fmi.carmanagement.repository.GarageRepository;
import pu.fmi.carmanagement.repository.MaintenanceRequestRepository;
import pu.fmi.carmanagement.service.CarService;
import pu.fmi.carmanagement.util.UtilMethods;

import java.util.*;

import static pu.fmi.carmanagement.util.Constants.CAR_NOT_FOUND_MSG;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final GarageRepository garageRepository;
    private final MaintenanceRequestRepository requestRepository;
    private final ModelMapper modelMapper;

    public CarServiceImpl(CarRepository carRepository, GarageRepository garageRepository, MaintenanceRequestRepository requestRepository, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.garageRepository = garageRepository;
        this.requestRepository = requestRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseCarDTO getCarById(Long id) {
        Optional<Car> inDB = carRepository.findById(id);
        UtilMethods.isSourceInDB(inDB, CAR_NOT_FOUND_MSG + id);

        Car source = inDB.get();
        ResponseCarDTO dto = modelMapper.map(source, ResponseCarDTO.class);
        return dto;
    }

    @Override
    public ResponseCarDTO updateCar(Long id, UpdateCarDTO carDTO) {
        Optional<Car> inDB = carRepository.findById(id);
        UtilMethods.isSourceInDB(inDB, CAR_NOT_FOUND_MSG + id);
        Car car = inDB.get();
        if (carDTO.getMake() != null && !carDTO.getMake().trim().isEmpty()) {
            car.setMake(carDTO.getMake());
        }
        if (carDTO.getModel() != null && !carDTO.getModel().trim().isEmpty()) {
            car.setModel(carDTO.getModel());
        }
        if (carDTO.getProductionYear() != null) {
            car.setProductionYear(carDTO.getProductionYear());
        }
        if (carDTO.getLicensePlate() != null && !carDTO.getLicensePlate().trim().isEmpty()) {
            car.setLicensePlate(carDTO.getLicensePlate());
        }
        if (carDTO.getGarageIds() != null && !carDTO.getGarageIds().isEmpty()) {
            List<Garage> garages = garageRepository.findByIdIn(carDTO.getGarageIds());
            if (garages == null || garages.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot update the car because the garage ids you entered are not in the system.");
            }
            car.setGarages(new HashSet<>(garages));
        }
        Car updatedCar = carRepository.save(car);
        return modelMapper.map(updatedCar, ResponseCarDTO.class);
    }

    @Override
    public Boolean deleteCar(Long id) {
        Optional<Car> car = carRepository.findById(id);
        UtilMethods.isSourceInDB(car, CAR_NOT_FOUND_MSG + id);

        // manually remove the car_id in maintenance_requests table
        List<MaintenanceRequest> requests = requestRepository.findAllByCar(car.get());
        if (requests != null && !requests.isEmpty()) {
            for (MaintenanceRequest request : requests) {
                request.setCar(null);
                requestRepository.save(request);
            }
        }

        carRepository.delete(car.get());
        return true;
    }

    @Override
    public List<ResponseCarDTO> getAllCarsWithFilters(String carMake, Long garageId, Integer fromYear, Integer toYear) {
        List<Car> found = carRepository.findByMakeAndYearRangeAndGarage(carMake, fromYear, toYear, garageId);
        List<ResponseCarDTO> dtos = new ArrayList<>();
        if (found != null || !found.isEmpty()) {
            dtos.addAll(
                    found.stream()
                            .map(c -> modelMapper.map(c, ResponseCarDTO.class))
                            .toList());
        }
        return dtos;
    }

    @Override
    public ResponseCarDTO createCar(CreateCarDTO carDTO) {
        Car car = new Car();
        car.setMake(carDTO.getMake());
        car.setModel(carDTO.getModel());
        car.setLicensePlate(carDTO.getLicensePlate());
        car.setProductionYear(carDTO.getProductionYear());
        if (carDTO.getGarageIds() != null && !carDTO.getGarageIds().isEmpty()) {
            Set<Garage> garages = new HashSet<>(garageRepository.findByIdIn(carDTO.getGarageIds()));
            car.setGarages(garages);
        }
        Car saved = carRepository.save(car);
        return modelMapper.map(saved, ResponseCarDTO.class);
    }
}
