package pu.fmi.carmanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "garages")
@Getter
@Setter
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
    private List<MaintenanceRequests> requests;
}
