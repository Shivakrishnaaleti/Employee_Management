package org.example.employee_management.dto.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDTO {

    private Integer addressId;
    private String street;
    private String city;
    private String state;
    private String pincode;

}
