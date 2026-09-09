package org.example.employee_management.dto.employee;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.employee_management.dto.address.AddressRequestDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDTO {

    @NotBlank(message = "Employee Name is required")
    private String employeeName;

    @NotBlank(message = "Email should not be null")
    @Email(message = "Invalid email")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$")
    private String phone;

    @Positive(message = "Salary must be greater than zero")
    private Double salary;

    @NotNull(message = "Department is required")
    private Integer departmentId;

    @Valid
    private AddressRequestDTO address;
}
