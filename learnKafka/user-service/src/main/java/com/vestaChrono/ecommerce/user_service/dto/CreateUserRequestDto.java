package com.vestaChrono.ecommerce.user_service.dto;

import lombok.Data;

@Data
public class CreateUserRequestDto {

    private Long id;
    private String fullName;
    private String email;
}
