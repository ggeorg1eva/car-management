package pu.fmi.carmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pu.fmi.carmanagement.model.entity.Car;
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}
