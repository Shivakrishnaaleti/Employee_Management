package org.example.employee_management.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.employee_management.dto.department.DepartmentRequestDTO;
import org.example.employee_management.dto.department.DepartmentResponseDTO;
import org.example.employee_management.service.DepartmentService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
@Validated
public class DepartmentController {

    private final DepartmentService service;


    @Operation(summary = "create department",
            description = "Creating a department....... ")
    @PostMapping
    public ResponseEntity<DepartmentResponseDTO> createDepartment(@Valid @RequestBody DepartmentRequestDTO dto){
        DepartmentResponseDTO responseDTO = service.createDepartment(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

    }

    @Operation(summary = "Get Departments",
            description = "Displaying all departments .....")
    @GetMapping
    public ResponseEntity<Page<DepartmentResponseDTO>> getAllDepartments(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "5") @Min(1) @Max(100) int size,
            @RequestParam(defaultValue = "departmentName")
            @Pattern(regexp = "departmentId|departmentName") String sortBy,
            @RequestParam(defaultValue = "asc") @Pattern(regexp = "(?i)asc|desc") String sortDir
            )
    {
        Page<DepartmentResponseDTO> responseDTO = service.getAllDepartments(page, size, sortBy, sortDir);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

    }

    @Operation(summary = "Get Department by deptId",
            description = "Displaying Departments produced by deptId")
    @GetMapping("/{deptId}")
    public ResponseEntity<DepartmentResponseDTO> getDeptById(@PathVariable Integer deptId){

        DepartmentResponseDTO dto = service.getDepartmentById(deptId);

        return ResponseEntity.ok(dto);

    }

    @Operation(summary = "Update department",
            description = "Displaying updated department by deptId")
    @PutMapping("/{deptId}")
    public ResponseEntity<DepartmentResponseDTO> updateDepartment(@PathVariable Integer deptId, @Valid @RequestBody DepartmentRequestDTO dto){

        DepartmentResponseDTO responseDTO = service.updateDepartment(deptId, dto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

    }

    @Operation(summary = "Delete departments",
            description = "Removing departments by deptId")
    @DeleteMapping("/{deptId}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Integer deptId){

        service.deleteDepartment(deptId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();


    }
}
