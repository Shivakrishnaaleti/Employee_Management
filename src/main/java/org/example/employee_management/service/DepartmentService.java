package org.example.employee_management.service;

import lombok.RequiredArgsConstructor;
import org.example.employee_management.dto.department.DepartmentRequestDTO;
import org.example.employee_management.dto.department.DepartmentResponseDTO;
import org.example.employee_management.entity.Department;
import org.example.employee_management.exception.DuplicateResourceException;
import org.example.employee_management.exception.ResourceNotFoundException;
import org.example.employee_management.mapper.DepartmentMapper;
import org.example.employee_management.repository.DepartmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper mapper;


    public DepartmentResponseDTO createDepartment(DepartmentRequestDTO dto){

        boolean dept = departmentRepository.existsByDepartmentName(dto.getDepartmentName());

        if(dept){
            throw new DuplicateResourceException("Department already exists with name : "+dto.getDepartmentName());
        }


        Department department = mapper.toEntity(dto);

        Department savedDepartment = departmentRepository.save(department);

        DepartmentResponseDTO response = mapper.toResponseDTO(savedDepartment);

        return  response;
    }

    public DepartmentResponseDTO getDepartmentById(Integer deptId){
        Department department = departmentRepository.findById(deptId).orElse(null);

        if(department == null){
            throw new ResourceNotFoundException("Department Not found with id : "+deptId);
        }

        DepartmentResponseDTO response = mapper.toResponseDTO(department);
        return response;
    }

    public Page<DepartmentResponseDTO> getAllDepartments(int page, int size, String sortBy, String sortDir){
        Sort sort;

        if (sortDir.equalsIgnoreCase("desc")){
            sort = Sort.by(sortBy).descending();
        }else{
            sort = Sort.by(sortBy).ascending();
        }
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Department> departments = departmentRepository.findAll(pageable);

        return departments.map(department -> mapper.toResponseDTO(department));
    }

    public DepartmentResponseDTO updateDepartment(Integer deptId, DepartmentRequestDTO dto){

        Department department = departmentRepository.findById(deptId).orElse(null);

        if(department == null){
            throw new ResourceNotFoundException("Department Not found with id : "+deptId);
        }

        if(!department.getDepartmentName().equalsIgnoreCase(dto.getDepartmentName()) &&
            departmentRepository.existsByDepartmentName(dto.getDepartmentName())){
            throw new DuplicateResourceException("Department already exists with name : "+ dto.getDepartmentName());
        }


        department.setDepartmentName(dto.getDepartmentName());

        Department updatedDepartment = departmentRepository.save(department);

        DepartmentResponseDTO response = mapper.toResponseDTO(updatedDepartment);

        return response;
    }

    public void deleteDepartment(Integer deptId){

        Department department = departmentRepository.findById(deptId).orElse(null);

        if(department == null){
            throw new ResourceNotFoundException("Department Not found with id : "+deptId);
        }

        departmentRepository.delete(department);
    }
}
