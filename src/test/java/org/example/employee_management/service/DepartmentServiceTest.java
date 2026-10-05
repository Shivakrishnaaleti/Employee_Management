package org.example.employee_management.service;

import org.example.employee_management.entity.Department;
import org.example.employee_management.exception.ResourceConflictException;
import org.example.employee_management.mapper.DepartmentMapper;
import org.example.employee_management.repository.DepartmentRepository;
import org.example.employee_management.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentMapper mapper;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    void deleteDepartmentWithEmployeesReturnsConflict() {
        int departmentId = 1;
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(new Department()));
        when(employeeRepository.existsByDepartment_DepartmentId(departmentId)).thenReturn(true);

        assertThrows(ResourceConflictException.class, () -> departmentService.deleteDepartment(departmentId));

        verify(departmentRepository, never()).delete(any(Department.class));
    }
}
