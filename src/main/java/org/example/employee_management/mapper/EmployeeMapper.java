package org.example.employee_management.mapper;


import org.example.employee_management.dto.address.AddressResponseDTO;
import org.example.employee_management.dto.department.DepartmentResponseDTO;
import org.example.employee_management.dto.employee.EmployeeRequestDTO;
import org.example.employee_management.dto.employee.EmployeeResponseDTO;
import org.example.employee_management.entity.Address;
import org.example.employee_management.entity.Department;
import org.example.employee_management.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public Employee toEntity(EmployeeRequestDTO dto, Department department){
        Employee employee = new Employee();

        employee.setEmployeeName(dto.getEmployeeName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setSalary(dto.getSalary());
        employee.setDepartment(department);

        if(dto.getAddress() != null){

            Address address = new Address();

            address.setStreet(dto.getAddress().getStreet());
            address.setCity(dto.getAddress().getCity());
            address.setState(dto.getAddress().getState());
            address.setPincode(dto.getAddress().getPincode());

            address.setEmployee(employee);

            employee.setAddress(address);
        }

        return employee;
    }
    public EmployeeResponseDTO toResponseDTO(Employee employee){

        EmployeeResponseDTO dto = new EmployeeResponseDTO();

        dto.setEmployeeId(employee.getEmployeeId());
        dto.setEmployeeName(employee.getEmployeeName());
        dto.setEmail(employee.getEmail());
        dto.setPhone(employee.getPhone());
        dto.setSalary(employee.getSalary());

        if(employee.getDepartment() != null){

            DepartmentResponseDTO departmentDTO = new DepartmentResponseDTO();

            departmentDTO.setDepartmentId(employee.getDepartment().getDepartmentId());
            departmentDTO.setDepartmentName(employee.getDepartment().getDepartmentName());

            dto.setDepartment(departmentDTO);
        }
        if(employee.getAddress() != null){

            AddressResponseDTO  addressDTO =  new AddressResponseDTO();

            addressDTO.setAddressId(employee.getAddress().getAddressId());
            addressDTO.setStreet(employee.getAddress().getStreet());
            addressDTO.setCity(employee.getAddress().getCity());
            addressDTO.setState(employee.getAddress().getState());
            addressDTO.setPincode(employee.getAddress().getPincode());

            dto.setAddress(addressDTO);
        }
        return dto;
    }
}
