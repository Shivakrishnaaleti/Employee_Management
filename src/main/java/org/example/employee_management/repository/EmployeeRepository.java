package org.example.employee_management.repository;

import org.example.employee_management.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    boolean existsByEmail(String email);
}
