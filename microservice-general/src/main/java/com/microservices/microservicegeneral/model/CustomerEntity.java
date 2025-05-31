package com.microservices.microservicegeneral.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    private String phone;

    // Relaciones
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<AddressEntity> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<OrderEntity> orders = new ArrayList<>();
}
