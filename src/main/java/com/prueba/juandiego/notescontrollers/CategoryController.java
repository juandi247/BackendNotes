package com.prueba.juandiego.notescontrollers;


import com.prueba.juandiego.dtos.CategoryDTO;
import com.prueba.juandiego.dtos.CategoryResponseDTO;
import com.prueba.juandiego.models.Category;
import com.prueba.juandiego.models.User;
import com.prueba.juandiego.services.CategoryService;
import com.prueba.juandiego.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;


    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDTO categoryDTO, Principal principal) {

        User user = userService.findUserByUsername(principal.getName());


        Category category = new Category();
        category.setName(categoryDTO.getName());

        category.setUser(user);

        Category createdCategory = categoryService.createCategory(category);

        return ResponseEntity.ok(createdCategory);
    }



    @GetMapping("/get")
    public ResponseEntity<List<CategoryResponseDTO>> getCategories(Principal principal) {

        // Obtener el nombre de usuario del usuario autenticado
        String username = principal.getName();

        // Obtener todas las categorías del usuario
        List<Category> categories = categoryService.getCategoriesByUser(username);

        // Convertir las categorías a CategoryResponseDTO
        List<CategoryResponseDTO> response = categories.stream()
                .map(category -> CategoryResponseDTO.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build())
                .toList();

        return ResponseEntity.ok(response);
    }


}

