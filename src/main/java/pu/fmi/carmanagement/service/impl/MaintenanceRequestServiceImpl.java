package pu.fmi.carmanagement.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pu.fmi.carmanagement.model.dto.request.CreateMaintenanceDTO;
import pu.fmi.carmanagement.model.dto.request.UpdateMaintenanceDTO;
import pu.fmi.carmanagement.model.dto.response.MonthlyRequestsReportDTO;
import pu.fmi.carmanagement.model.dto.response.ResponseMaintenanceDTO;
import pu.fmi.carmanagement.model.entity.Car;
import pu.fmi.carmanagement.model.entity.Garage;
import pu.fmi.carmanagement.model.entity.MaintenanceRequest;
import pu.fmi.carmanagement.repository.CarRepository;
import pu.fmi.carmanagement.repository.GarageRepository;
import pu.fmi.carmanagement.repository.MaintenanceRequestRepository;
import pu.fmi.carmanagement.service.MaintenanceRequestService;
import pu.fmi.carmanagement.util.UtilMethods;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static pu.fmi.carmanagement.util.Constants.MAINTENANCE_REQUEST_NOT_FOUND_MSG;

@Service
public class MaintenanceRequestServiceImpl implements MaintenanceRequestService {
    private final MaintenanceRequestRepository requestRepository;
    private final CarRepository carRepository;
    private final GarageRepository garageRepository;
    private final ModelMapper modelMapper;

    public MaintenanceRequestServiceImpl(MaintenanceRequestRepository requestRepository, CarRepository carRepository, GarageRepository garageRepository, ModelMapper modelMapper) {
        this.requestRepository = requestRepository;
        this.carRepository = carRepository;
        this.garageRepository = garageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseMaintenanceDTO getRequestById(Long id) {
        Optional<MaintenanceRequest> inDB = requestRepository.findById(id);
        UtilMethods.isSourceInDB(inDB, MAINTENANCE_REQUEST_NOT_FOUND_MSG + id);

        MaintenanceRequest source = inDB.get();
        ResponseMaintenanceDTO dto = modelMapper.map(source, ResponseMaintenanceDTO.class);
        dto.setCarName(getCarNameFromSourceMakeAndModel(source));
        return dto;
    }

    @Override
    public ResponseMaintenanceDTO updateRequest(Long id, UpdateMaintenanceDTO requestDTO) {
        Optional<MaintenanceRequest> inDB = requestRepository.findById(id);
        UtilMethods.isSourceInDB(inDB, MAINTENANCE_REQUEST_NOT_FOUND_MSG + id);
        MaintenanceRequest source = inDB.get();
        if (requestDTO.getCarId() != null) {
            if (carRepository.findById(requestDTO.getCarId()).isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The car you are trying to set cannot be found");
            }
            source.setCar(carRepository.findById(requestDTO.getCarId()).get());
        }
        if (requestDTO.getServiceType() != null && !requestDTO.getServiceType().trim().isEmpty()) {
            source.setServiceType(requestDTO.getServiceType());
        }
        if (requestDTO.getScheduledDate() != null) {
            source.setScheduledDate(requestDTO.getScheduledDate());
        }
        if (requestDTO.getGarageId() != null) {
            Optional<Garage> garage = garageRepository.findById(requestDTO.getGarageId());
            if (garage.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The garage you are trying to set cannot be found");
            }
            if (garage.get().getRequests().size() == garage.get().getCapacity()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot update this request because there is no capacity in the garage for the current date. Please choose a new date or a different garage!");
            }
            source.setGarage(garage.get());
        }
        requestRepository.save(source);
        ResponseMaintenanceDTO dto = modelMapper.map(source, ResponseMaintenanceDTO.class);
        dto.setCarName(getCarNameFromSourceMakeAndModel(source));
        return dto;
    }

    @Override
    public Boolean deleteRequest(Long id) {
        Optional<MaintenanceRequest> inDB = requestRepository.findById(id);
        UtilMethods.isSourceInDB(inDB, MAINTENANCE_REQUEST_NOT_FOUND_MSG + id);
        MaintenanceRequest source = inDB.get();
        requestRepository.delete(source);
        return true;
    }

    @Override
    public List<ResponseMaintenanceDTO> getAllRequestsWithFilters(Long carId, Long garageId, LocalDate startDate, LocalDate endDate) {
        List<MaintenanceRequest> foundRequests = requestRepository.findAllByParams(carId, garageId, startDate, endDate);
        if (foundRequests == null && foundRequests.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No maintenance requests found for the applied filters.");
        }
        List<ResponseMaintenanceDTO> dtos = foundRequests.stream()
                .map(r -> {
                    ResponseMaintenanceDTO dto = modelMapper.map(r, ResponseMaintenanceDTO.class);
                    dto.setCarName(getCarNameFromSourceMakeAndModel(r));
                    return dto;
                }).toList();

        return dtos;
    }

    @Override
    public ResponseMaintenanceDTO createRequest(CreateMaintenanceDTO requestDTO) {
        Optional<Car> car = carRepository.findById(requestDTO.getCarId());
        if (car.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot create this request because there is no such car in the system!");
        }
        Optional<Garage> garage = garageRepository.findById(requestDTO.getGarageId());
        if (garage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot create this request because there is no such garage in the system!");
        }
        if (garage.get().getRequests().size() == garage.get().getCapacity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot create this request because there is no capacity in the garage for the current date. Please choose a new date or a different garage!");
        }
        MaintenanceRequest newRequest = new MaintenanceRequest();
        newRequest.setGarage(garage.get());
        newRequest.setCar(car.get());
        newRequest.setServiceType(requestDTO.getServiceType());
        newRequest.setScheduledDate(requestDTO.getScheduledDate());

        MaintenanceRequest savedRequest = requestRepository.save(newRequest);
        ResponseMaintenanceDTO responseDto = modelMapper.map(savedRequest, ResponseMaintenanceDTO.class);
        responseDto.setCarName(getCarNameFromSourceMakeAndModel(savedRequest));

        return responseDto;
    }

    @Override
    public List<MonthlyRequestsReportDTO> getMonthlyRequestsReport(Long garageId, String startMonth, String endMonth) {
        Optional<Garage> garage = garageRepository.findById(garageId);
        if (garage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid garage id!");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth stYearMonth = YearMonth.parse(startMonth, formatter);
        LocalDate startDate = stYearMonth.atDay(1);

        YearMonth endYearMonth = YearMonth.parse(endMonth, formatter);
        LocalDate endDate = endYearMonth.atEndOfMonth();
        List<MaintenanceRequest> found = requestRepository.findAllByGarageAndScheduledDateBetween
                (
                        garage.get(),
                        startDate,
                        endDate
                );

        if (found == null || found.isEmpty()) {
            return new ArrayList<>();
        }
        List<MonthlyRequestsReportDTO> dtos = new ArrayList<>();
        String[] start = startMonth.split("-");
        String[] end = endMonth.split("-");
        YearMonth startMonthObj = YearMonth.of(Integer.parseInt(start[0]), Integer.parseInt(start[1]));
        YearMonth endMonthObj = YearMonth.of(Integer.parseInt(end[0]), Integer.parseInt(end[1]));
        for (YearMonth ym = YearMonth.of(startMonthObj.getYear(), startMonthObj.getMonth()); !ym.isAfter(endMonthObj); ym = ym.plusMonths(1L)) {
            MonthlyRequestsReportDTO dto = new MonthlyRequestsReportDTO();
            dto.setYearMonth(ym);
            YearMonth finalYm = ym;
            dto.setRequests(found.stream()
                    .filter(r ->
                            r.getScheduledDate().getYear() == finalYm.getYear()
                                    && r.getScheduledDate().getMonth() == finalYm.getMonth())
                    .count());
            dtos.add(dto);
        }
        return dtos;
    }

    private String getCarNameFromSourceMakeAndModel(MaintenanceRequest source) {
        return String.format("%s %s", source.getCar().getMake(), source.getCar().getModel());
    }
}
