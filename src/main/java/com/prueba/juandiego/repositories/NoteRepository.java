package com.prueba.juandiego.repositories;

import com.prueba.juandiego.models.Note;
import com.prueba.juandiego.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUser(User user); // Para obtener notas de un usuario específico
    List<Note> findByUserUsernameAndArchived(String username, boolean archived);

    List<Note> findByCategory_NameAndUser_Username(String categoryName, String username);
}
