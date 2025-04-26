package com.fawy_internship.gateway.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@Builder
@ToString
public class GenericResponse {
    private String status;
    private String message;
}
