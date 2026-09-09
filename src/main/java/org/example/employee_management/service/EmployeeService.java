package org.example.employee_management.service;

import lombok.RequiredArgsConstructor;
import org.example.employee_management.dto.employee.EmployeeRequestDTO;
import org.example.employee_management.dto.employee.EmployeeResponseDTO;
import org.example.employee_management.entity.Address;
import org.example.employee_management.entity.Department;
import org.example.employee_management.entity.Employee;
import org.example.employee_management.exception.DuplicateResourceException;
import org.example.employee_management.exception.ResourceNotFoundException;
import org.example.employee_management.mapper.EmployeeMapper;
import org.example.employee_management.repository.DepartmentRepository;
import org.example.employee_management.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper mapper;

    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto){

        boolean emailExists = employeeRepository.existsByEmail(dto.getEmail());

        if(emailExists){
            throw new DuplicateResourceException("Email already exists with : "+ dto.getEmail());

        }

        Department department = departmentRepository.findById(dto.getDepartmentId()).orElse(null);

        if(department == null){
            throw new ResourceNotFoundException("Resource not found with id : "+ dto.getDepartmentId());

        }

        Employee employee = mapper.toEntity(dto, department);

        Employee savedEmployee = employeeRepository.save(employee);

        EmployeeResponseDTO response = mapper.toResponseDTO(savedEmployee);

        return response;
    }
    public EmployeeResponseDTO getEmployeeById(Integer employeeId){

        Employee employee = employeeRepository.findById(employeeId).orElse(null);

        if(employee == null){
            throw new ResourceNotFoundException("Employee not found with id : "+ employeeId);
        }

        EmployeeResponseDTO response = mapper.toResponseDTO(employee);

        return response;
    }
    public List<EmployeeResponseDTO> getAllEmployees(int page , int size, String sortBy, String sortDir){

        Sort sort;

        if(sortDir.equalsIgnoreCase("desc")){
            sort = Sort.by(sortBy).descending();
        }else{
            sort = Sort.by(sortBy).ascending();
        }
        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Employee> employees = employeeRepository.findAll(pageable);

        return employees.map(employee -> mapper.toResponseDTO(employee)).getContent();

    }

    public EmployeeResponseDTO updateEmployee(Integer employeeId, EmployeeRequestDTO dto){

        Employee employee = employeeRepository.findById(employeeId).orElse(null);

        if (employee == null){
            throw new ResourceNotFoundException("Employee not found with id : "+ employeeId );
        }

        Department department = departmentRepository.findById(dto.getDepartmentId()).orElse(null);

        if (department == null ){
            throw new ResourceNotFoundException("Department not found with id : "+dto.getDepartmentId());
        }

        if(!employee.getEmail().equals(dto.getEmail())){
            boolean existsEmail = employeeRepository.existsByEmail(dto.getEmail());

            if(existsEmail){
                throw new DuplicateResourceException("Email already exists with : "+dto.getEmail());
            }
        }

        employee.setEmployeeName(dto.getEmployeeName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setSalary(dto.getSalary());
        employee.setDepartment(department);

        if(dto.getAddress() != null) {
            Address address = employee.getAddress();
            if (address == null) {
                address = new Address();

                employee.setAddress(address);
            }
            address.setStreet(dto.getAddress().getStreet());
            address.setCity(dto.getAddress().getCity());
            address.setState(dto.getAddress().getState());
            address.setPincode(dto.getAddress().getPincode());

            address.setEmployee(employee);
        }

        Employee updatedEmployee = employeeRepository.save(employee);

        return mapper.toResponseDTO(updatedEmployee);

    }

    public void deleteEmployee(Integer employeeId){
        Employee employee = employeeRepository.findById(employeeId).orElse(null);

        if(employee == null){
            throw new ResourceNotFoundException("Employee not found with id : "+employeeId);
        }

        employeeRepository.delete(employee);
    }

}
