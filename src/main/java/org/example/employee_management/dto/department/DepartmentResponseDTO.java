package org.example.employee_management.dto.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer departmentId;
    private String departmentName;

}
