package com.vestaChrono.cashingApp.dto;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDto implements Serializable {

    private Long id;
    private String name;
    private String email;
    private Double salary;

}
