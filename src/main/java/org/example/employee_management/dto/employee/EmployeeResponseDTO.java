package org.example.employee_management.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.employee_management.dto.address.AddressResponseDTO;
import org.example.employee_management.dto.department.DepartmentResponseDTO;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer employeeId;
    private String employeeName;
    private String email;
    private String phone;
    private Double salary;

    private DepartmentResponseDTO department;

    private AddressResponseDTO address;
    
}
