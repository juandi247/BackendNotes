package com.prueba.juandiego.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteResponseDTO {
    private Long id; // El ID de la nota
    private String title; // Título de la nota
    private String content; // Contenido de la nota
    private boolean archived; // Estado de archivado
    private String categoryName; // Nombre de la categoría (si aplica)
}
