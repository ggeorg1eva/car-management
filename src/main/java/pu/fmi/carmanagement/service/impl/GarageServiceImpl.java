package pu.fmi.carmanagement.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pu.fmi.carmanagement.model.dto.request.UpdateGarageDTO;
import pu.fmi.carmanagement.model.dto.response.ResponseGarageDTO;
import pu.fmi.carmanagement.model.entity.Garage;
import pu.fmi.carmanagement.repository.GarageRepository;
import pu.fmi.carmanagement.service.GarageService;
import pu.fmi.carmanagement.util.UtilMethods;

import java.util.Optional;

import static pu.fmi.carmanagement.util.Constants.GARAGE_NOT_FOUND_MSG;

@Service
public class GarageServiceImpl implements GarageService {
    private final GarageRepository garageRepository;
    private final ModelMapper modelMapper;

    public GarageServiceImpl(GarageRepository garageRepository, ModelMapper modelMapper) {
        this.garageRepository = garageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseGarageDTO getGarageById(Long id) {
        Optional<Garage> inDB = garageRepository.findById(id);
        UtilMethods.isSourceInDB(inDB, GARAGE_NOT_FOUND_MSG + id);

        Garage source = inDB.get();
        ResponseGarageDTO dto = modelMapper.map(inDB, ResponseGarageDTO.class);
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
    public Boolean deleteGarage(Long id) {
//todo
//        Optional<Garage> inDB = garageRepository.findById(id);
//        UtilMethods.isSourceInDB(inDB, GARAGE_NOT_FOUND_MSG + id);
//        Garage source = inDB.get();
//        garageRepository.delete(source);
//        return !garageRepository.existsById(id);
    }
}
