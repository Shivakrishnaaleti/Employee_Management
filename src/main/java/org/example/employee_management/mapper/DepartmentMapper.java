package org.example.employee_management.mapper;

import org.example.employee_management.dto.department.DepartmentRequestDTO;
import org.example.employee_management.dto.department.DepartmentResponseDTO;
import org.example.employee_management.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {
    public Department toEntity(DepartmentRequestDTO dto){

        Department department = new Department();

        department.setDepartmentName(dto.getDepartmentName());
        return department;
    }
    public DepartmentResponseDTO toResponseDTO(Department department){

        DepartmentResponseDTO dto = new DepartmentResponseDTO();
        dto.setDepartmentId(department.getDepartmentId());
        dto.setDepartmentName(department.getDepartmentName());

        return dto;
    }
}
