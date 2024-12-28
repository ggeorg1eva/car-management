package pu.fmi.carmanagement.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "garages")
public class Garage extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false, columnDefinition = "BIGINT default 0")
    private Long capacity;
    @OneToMany(mappedBy = "garage")
    private List<MaintenanceRequest> requests;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public List<MaintenanceRequest> getRequests() {
        return requests;
    }

    public void setRequests(List<MaintenanceRequest> requests) {
        this.requests = requests;
    }
}
