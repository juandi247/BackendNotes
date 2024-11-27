package com.prueba.juandiego.services;

import com.prueba.juandiego.dtos.NoteRequestDTO;
import com.prueba.juandiego.models.Category;
import com.prueba.juandiego.models.Note;
import com.prueba.juandiego.models.User;
import com.prueba.juandiego.repositories.CategoryRepository;
import com.prueba.juandiego.repositories.NoteRepository;
import com.prueba.juandiego.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public Note createNote(NoteRequestDTO noteDTO, String username) {
        // Obtener el usuario de la base de datos usando el username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Obtener la categoría por nombre (usando el método findByName que devuelve un Optional<Category>)
        Category category = categoryRepository.findByName(noteDTO.getCategoryname());


        // Crear la nota
        Note note = new Note();
        note.setTitle(noteDTO.getTitle());
        note.setContent(noteDTO.getContent());
        note.setUser(user);  // Asociar la nota al usuario
        note.setCategory(category);  // Asociar la nota a la categoría

        // Guardar la nota en la base de datos
        return noteRepository.save(note);
    }





    @Transactional
    public void deleteNote(Long id, String username) {
        // Obtener el usuario autenticado desde el username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Buscar la nota por id
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        // Verificar que la nota pertenece al usuario autenticado
        if (!note.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not allowed to delete this note");
        }

        // Eliminar la nota
        noteRepository.delete(note);
    }





    @Transactional
    public Note updateNote(Long id, NoteRequestDTO noteDTO, String username) {
        // Obtener la nota por id
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        // Verificar si la nota pertenece al usuario autenticado
        if (!note.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You do not have permission to edit this note");
        }

        // Actualizar los campos de la nota
        note.setTitle(noteDTO.getTitle());
        note.setContent(noteDTO.getContent());

        // Si la categoría ha sido modificada, actualizarla también
        if (noteDTO.getCategoryname() != null) {
            Category category = categoryRepository.findByName(noteDTO.getCategoryname());
            note.setCategory(category);
        }

        // Guardar los cambios en la base de datos
        return noteRepository.save(note);
    }







    @Transactional
    public Note archiveNote(Long id, String username) {
        // Obtener la nota por id
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        // Verificar si la nota pertenece al usuario autenticado
        if (!note.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You do not have permission to archive this note");
        }

        // Actualizar el campo "archived" a true
        note.setArchived(true);

        // Guardar los cambios en la base de datos
        return noteRepository.save(note);
    }






    @Transactional
    public Note unarchiveNote(Long id, String username) {
        // Obtener la nota por id
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        // Verificar si la nota pertenece al usuario autenticado
        if (!note.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You do not have permission to unarchive this note");
        }

        // Actualizar el campo "archived" a false
        note.setArchived(false);

        // Guardar los cambios en la base de datos
        return noteRepository.save(note);
    }







    @Transactional(readOnly=true)
    public List<Note> getArchivedNotes(String username) {
        // Consultar las notas archivadas del usuario
        return noteRepository.findByUserUsernameAndArchived(username, true);
    }

    @Transactional(readOnly = true)
    public List<Note> getUnarchivedNotes(String username) {
        // Consultar las notas no archivadas del usuario
        return noteRepository.findByUserUsernameAndArchived(username, false);
    }


//list for each category

    public List<Note> getNotesByCategory(String categoryName, String username) {
        return noteRepository.findByCategory_NameAndUser_Username(categoryName, username);
    }
}

    // Otros métodos como listar notas, actualizar, eliminar, etc.





