package com.prueba.juandiego.services;


import com.prueba.juandiego.models.Category;
import com.prueba.juandiego.models.User;
import com.prueba.juandiego.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    //Method to create a category


    public Optional<Category> findByNameAndUser(String categoryName, User user) {
        return categoryRepository.findByNameAndUser(categoryName, user);
    }




    public Category createCategory(Category category) {

        // Verificar si la categoría con ese nombre ya existe
        if (categoryRepository.findByName(category.getName()) != null) {
            throw new RuntimeException("Category with this name already exists");
        }

        // Guardar la categoría en la base de datos
        return categoryRepository.save(category);
    }
}

