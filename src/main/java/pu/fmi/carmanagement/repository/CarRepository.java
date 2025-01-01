package pu.fmi.carmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pu.fmi.carmanagement.model.entity.Car;
import pu.fmi.carmanagement.model.entity.Garage;

import java.util.List;
import java.util.Set;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByGaragesContaining(Set<Garage> garages);

    @Query("SELECT c FROM Car c " +
            "JOIN c.garages g " +
            "WHERE (:make IS NULL OR :make = '' or c.make = :make) " +
            "AND (:fromYear IS NULL OR c.productionYear >= :fromYear) " +
            "AND (:toYear IS NULL OR c.productionYear <= :toYear) " +
            "AND (:garageId IS NULL OR g.id = :garageId)")
    List<Car> findByMakeAndYearRangeAndGarage(
            @Param("make") String make,
            @Param("fromYear") Integer fromYear,
            @Param("toYear") Integer toYear,
            @Param("garageId") Long garageId
    );
}
