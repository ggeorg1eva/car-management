package pu.fmi.carmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pu.fmi.carmanagement.model.entity.Car;
import pu.fmi.carmanagement.model.entity.Garage;

import java.util.List;
import java.util.Set;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByGaragesContaining(Set<Garage> garages);
}
