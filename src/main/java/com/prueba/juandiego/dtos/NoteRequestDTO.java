package com.prueba.juandiego.dtos;

import com.prueba.juandiego.models.Category;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

//Dto to validate the data when a user create a new Note

@Data
public class NoteRequestDTO {

    //validate that the title is not empty
    private String title;

    //The message can be empty for the user, but the title not

    private String content;


    private String categoryname;

    }
