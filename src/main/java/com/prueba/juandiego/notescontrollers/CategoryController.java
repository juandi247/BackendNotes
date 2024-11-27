package com.prueba.juandiego.notescontrollers;


import com.prueba.juandiego.dtos.CategoryDTO;
import com.prueba.juandiego.models.Category;
import com.prueba.juandiego.models.User;
import com.prueba.juandiego.services.CategoryService;
import com.prueba.juandiego.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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


}

