package org.example.employee_management.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;

    @Column(nullable = false)
    private String employeeName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    private Double salary;

    @ManyToOne
    @JoinColumn(name = "department_id",nullable = false)
    private Department department;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "address_id", unique = true)
    private Address address;

}
