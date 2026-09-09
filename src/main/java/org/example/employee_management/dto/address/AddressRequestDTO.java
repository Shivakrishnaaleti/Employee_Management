package org.example.employee_management.dto.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestDTO {

    @NotBlank(message = "Street Name is required")
    private String street;

    @NotBlank(message = "city Name is required")
    private String city;

    @NotBlank(message = "state Name is required")
    private String state;

    @NotBlank(message = "pincode must Contain Only 6 Numbers")
    @Pattern(regexp = "^[0-9]{6}$")
    private String pincode;

}
