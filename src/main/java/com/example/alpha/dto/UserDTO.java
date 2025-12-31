package com.example.alpha.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotEmpty
    @Schema(description = "The user's username")
    private String userName;
    private String email;
    private boolean sentimentAnalysis;
    @NotEmpty
    private String password;
}
