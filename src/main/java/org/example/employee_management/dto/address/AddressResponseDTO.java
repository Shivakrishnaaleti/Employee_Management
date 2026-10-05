package org.example.employee_management.dto.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer addressId;
    private String street;
    private String city;
    private String state;
    private String pincode;

}
