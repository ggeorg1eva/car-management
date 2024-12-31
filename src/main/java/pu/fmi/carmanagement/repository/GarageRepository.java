package pu.fmi.carmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pu.fmi.carmanagement.model.entity.Garage;

import java.util.List;

@Repository
public interface GarageRepository extends JpaRepository<Garage, Long> {
    List<Garage> findAllByCity(String city);
}
