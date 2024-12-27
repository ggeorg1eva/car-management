package pu.fmi.carmanagement.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "maintenance_requests")
@Getter
@Setter
public class MaintenanceRequests extends BaseEntity {
    @Column(name = "service_type", nullable = false)
    private String serviceType;
    @Column(name = "scheduled_date", nullable = false)
    private LocalDate scheduledDate;
    @ManyToOne
    private Car car;
    @ManyToOne
    private Garage garage;
}
