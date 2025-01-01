package pu.fmi.carmanagement.service.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pu.fmi.carmanagement.model.dto.request.CreateGarageDTO;
import pu.fmi.carmanagement.model.dto.request.UpdateGarageDTO;
import pu.fmi.carmanagement.model.dto.response.GarageDailyAvailabilityReportDTO;
import pu.fmi.carmanagement.model.dto.response.ResponseGarageDTO;
import pu.fmi.carmanagement.model.entity.Car;
import pu.fmi.carmanagement.model.entity.Garage;
import pu.fmi.carmanagement.model.entity.MaintenanceRequest;
import pu.fmi.carmanagement.repository.CarRepository;
import pu.fmi.carmanagement.repository.GarageRepository;
import pu.fmi.carmanagement.repository.MaintenanceRequestRepository;
import pu.fmi.carmanagement.service.GarageService;
import pu.fmi.carmanagement.util.UtilMethods;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static pu.fmi.carmanagement.util.Constants.GARAGE_NOT_FOUND_MSG;

@Service
public class GarageServiceImpl implements GarageService {
    private final GarageRepository garageRepository;
    private final CarRepository carRepository;
    private final MaintenanceRequestRepository requestRepository;
    private final ModelMapper modelMapper;

    public GarageServiceImpl(GarageRepository garageRepository, CarRepository carRepository, MaintenanceRequestRepository requestRepository, ModelMapper modelMapper) {
        this.garageRepository = garageRepository;
        this.carRepository = carRepository;
        this.requestRepository = requestRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseGarageDTO getGarageById(Long id) {
        Optional<Garage> inDB = garageRepository.findById(id);
        UtilMethods.isSourceInDB(inDB, GARAGE_NOT_FOUND_MSG + id);

        Garage source = inDB.get();
        ResponseGarageDTO dto = modelMapper.map(source, ResponseGarageDTO.class);
        return dto;
    }

    @Override
    public ResponseGarageDTO updateGarage(Long id, UpdateGarageDTO garageDTO) {
        Optional<Garage> inDB = garageRepository.findById(id);
        UtilMethods.isSourceInDB(inDB, GARAGE_NOT_FOUND_MSG + id);
        Garage source = inDB.get();

        if (garageDTO.getCapacity() != null) {
            source.setCapacity(garageDTO.getCapacity());
        }
        if (garageDTO.getCity() != null && !garageDTO.getCity().trim().isEmpty()) {
            source.setCity(garageDTO.getCity());
        }
        if (garageDTO.getLocation() != null && !garageDTO.getLocation().trim().isEmpty()) {
            source.setLocation(garageDTO.getLocation());
        }
        if (garageDTO.getName() != null && !garageDTO.getName().trim().isEmpty()) {
            source.setName(garageDTO.getName());
        }
        garageRepository.save(source);
        ResponseGarageDTO dto = modelMapper.map(source, ResponseGarageDTO.class);
        return dto;
    }

    @Override
    @Transactional
    public Boolean deleteGarage(Long id) {
        Optional<Garage> garage = garageRepository.findById(id);
        UtilMethods.isSourceInDB(garage, GARAGE_NOT_FOUND_MSG + id);

        // manually remove the garage_id in the cars-garages table
        List<Car> cars = carRepository.findAllByGaragesContaining(Set.of(garage.get()));
        if (cars != null && !cars.isEmpty()) {
            for (Car car : cars) {
                car.getGarages().remove(garage.get());
                carRepository.save(car);
            }
        }
        // manually remove the garage_id in maintenance_requests table
        List<MaintenanceRequest> requests = requestRepository.findAllByGarage(garage.get());
        if (requests != null && !requests.isEmpty()) {
            for (MaintenanceRequest request : requests) {
                request.setGarage(null);
                requestRepository.save(request);
            }
        }

        garageRepository.delete(garage.get());
        return true;
    }

    @Override
    public ResponseGarageDTO createGarage(CreateGarageDTO garageDTO) {
        Garage garage = new Garage();
        garage.setName(garageDTO.getName());
        garage.setLocation(garageDTO.getLocation());
        garage.setCity(garageDTO.getCity());
        garage.setCapacity(garageDTO.getCapacity());
        Garage savedGarage = garageRepository.save(garage);
        return modelMapper.map(savedGarage, ResponseGarageDTO.class);
    }

    @Override
    public List<ResponseGarageDTO> getAllGaragesByCity(String city) {
        List<Garage> foundInDB;
        if (city != null && !city.trim().isEmpty()) {
            foundInDB = garageRepository.findAllByCity(city);
        } else {
            foundInDB = garageRepository.findAll();
        }
        return foundInDB.stream()
                .map(g -> modelMapper.map(g, ResponseGarageDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GarageDailyAvailabilityReportDTO> getDailyAvailabilityReport(Long garageId, String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        Optional<Garage> inDB = garageRepository.findById(garageId);
        UtilMethods.isSourceInDB(inDB, GARAGE_NOT_FOUND_MSG);

        List<GarageDailyAvailabilityReportDTO> dtos = new ArrayList<>();

        for (LocalDate s = LocalDate.of(start.getYear(), start.getMonth(), start.getDayOfMonth()); !s.isAfter(end); s = s.plusDays(1L)) {
            GarageDailyAvailabilityReportDTO dto = new GarageDailyAvailabilityReportDTO();
            dto.setDate(s.toString());
            LocalDate finalS = s;
            long requestsCount = inDB.get().getRequests().stream().filter(r -> r.getScheduledDate().equals(finalS)).count();
            dto.setRequests(requestsCount);
            dto.setAvailableCapacity(inDB.get().getCapacity() - requestsCount);
            dtos.add(dto);
        }
        return dtos;
    }
}
