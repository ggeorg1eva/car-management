package pu.fmi.carmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pu.fmi.carmanagement.model.entity.Garage;
import pu.fmi.carmanagement.model.entity.MaintenanceRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, Long> {
    Optional<MaintenanceRequest> findById(Long id);

    List<MaintenanceRequest> findAllByGarage(Garage garage);

    List<MaintenanceRequest> findAllByGarageAndScheduledDateBetween(Garage garage, LocalDate startDate, LocalDate endDate);

    @Query("SELECT m FROM MaintenanceRequest m " +
            "WHERE (:carId IS NULL OR m.car.id = :carId) " +
            "AND (:garageId IS NULL OR m.garage.id = :garageId) " +
            "AND (:startDate IS NULL OR m.scheduledDate >= :startDate) " +
            "AND (:endDate IS NULL OR m.scheduledDate <= :endDate)")
    List<MaintenanceRequest> findAllByParams(
            @Param("carId") Long carId,
            @Param("garageId") Long garageId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
