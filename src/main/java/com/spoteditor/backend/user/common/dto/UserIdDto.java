package com.spoteditor.backend.user.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserTokenDto {
    private Long id;
    private String role;
}
