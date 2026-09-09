package org.example.employee_management.repository;

import org.example.employee_management.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    boolean existsByDepartmentName(String departmentName);
}
