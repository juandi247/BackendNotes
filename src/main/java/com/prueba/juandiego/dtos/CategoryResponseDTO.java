package com.prueba.juandiego.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponseDTO {
    private Long id;
    private String name;
}
