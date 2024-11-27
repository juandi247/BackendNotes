package com.prueba.juandiego.repositories;


import com.prueba.juandiego.models.Category;
import com.prueba.juandiego.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);


    Optional<Category> findByNameAndUser(String name, User user);

    List<Category> findAllByUser(User user);

}



