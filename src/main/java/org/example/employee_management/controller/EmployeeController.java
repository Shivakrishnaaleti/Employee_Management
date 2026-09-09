package org.example.employee_management.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.employee_management.dto.employee.EmployeeRequestDTO;
import org.example.employee_management.dto.employee.EmployeeResponseDTO;
import org.example.employee_management.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @Operation(summary = "create a employee",
               description = "Creating a employee......")
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO dto){

        EmployeeResponseDTO response = service.createEmployee(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @Operation(summary = "Get employees",
            description = "displaying all employees....")
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "employeeName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ){
        List<EmployeeResponseDTO> responseDTO = service.getAllEmployees(page, size, sortBy, sortDir);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @Operation(summary = "get employee by employeeId",
            description = "displaying employee produced by  employeeId")
    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable int employeeId){
        EmployeeResponseDTO responseDTO = service.getEmployeeById(employeeId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

    }

    @Operation(summary = "update employee by employeeId",
            description = "displaying a updated employee produced by employeeId")
    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable int employeeId, @Valid @RequestBody EmployeeRequestDTO dto){
        EmployeeResponseDTO responseDTO = service.updateEmployee(employeeId,dto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

    }

    @Operation(summary = "delete employee by employeeId",
            description = "Removing an employee ")
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable  int employeeId){

        service.deleteEmployee(employeeId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
