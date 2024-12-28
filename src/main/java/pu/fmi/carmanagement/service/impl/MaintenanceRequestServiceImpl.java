package pu.fmi.carmanagement.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pu.fmi.carmanagement.model.dto.request.UpdateMaintenanceDTO;
import pu.fmi.carmanagement.model.dto.response.ResponseMaintenanceDTO;
import pu.fmi.carmanagement.model.entity.MaintenanceRequest;
import pu.fmi.carmanagement.repository.CarRepository;
import pu.fmi.carmanagement.repository.GarageRepository;
import pu.fmi.carmanagement.repository.MaintenanceRequestRepository;
import pu.fmi.carmanagement.service.MaintenanceRequestService;
import pu.fmi.carmanagement.util.UtilService;

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
        UtilService.isSourceInDB(inDB, MAINTENANCE_REQUEST_NOT_FOUND_MSG + id);

        MaintenanceRequest source = inDB.get();
        ResponseMaintenanceDTO dto = modelMapper.map(inDB, ResponseMaintenanceDTO.class);
        dto.setCarName(String.format("%s %s", source.getCar().getMake(), source.getCar().getModel()));
        return dto;
    }

    @Override
    public ResponseMaintenanceDTO updateRequest(Long id, UpdateMaintenanceDTO requestDTO) {
        Optional<MaintenanceRequest> inDB = requestRepository.findById(id);
        UtilService.isSourceInDB(inDB, MAINTENANCE_REQUEST_NOT_FOUND_MSG + id);
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
            if (garageRepository.findById(requestDTO.getGarageId()).isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The garage you are trying to set cannot be found");
            }
            source.setGarage(garageRepository.findById(requestDTO.getGarageId()).get());
        }
        requestRepository.save(source);
        ResponseMaintenanceDTO dto = modelMapper.map(source, ResponseMaintenanceDTO.class);
        dto.setCarName(String.format("%s %s", source.getCar().getMake(), source.getCar().getModel()));
        return dto;
    }
}
