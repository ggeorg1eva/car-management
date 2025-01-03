package pu.fmi.carmanagement.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "maintenance_requests")
public class MaintenanceRequest extends BaseEntity {
    @Column(name = "service_type", nullable = false)
    private String serviceType;
    @Column(name = "scheduled_date", nullable = false)
    private LocalDate scheduledDate;
    @ManyToOne
    @JoinColumn(name = "car_id", nullable = true)
    private Car car;
    @ManyToOne
    @JoinColumn(name = "garage_id", nullable = true)
    private Garage garage;

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }
}
