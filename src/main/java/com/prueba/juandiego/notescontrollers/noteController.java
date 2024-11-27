package com.prueba.juandiego.notescontrollers;


import com.prueba.juandiego.dtos.NoteRequestDTO;
import com.prueba.juandiego.dtos.NoteResponseDTO;
import com.prueba.juandiego.models.Note;
import com.prueba.juandiego.services.CategoryService;
import com.prueba.juandiego.services.NoteService;
import com.prueba.juandiego.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/note")
@RequiredArgsConstructor
public class noteController {

    private final NoteService noteService;
    private final UserService userService;
    private final CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<Note> createNote(@RequestBody NoteRequestDTO noteDTO, Principal principal) {
        // Obtener el username del usuario autenticado
        String username = principal.getName();

        // Llamar al servicio para crear la nota
        Note createdNote = noteService.createNote(noteDTO, username);

        return ResponseEntity.ok(createdNote);
    }



    // Método para eliminar una nota
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id, Principal principal) {
        String username = principal.getName(); // Obtener el nombre de usuario desde el JWT
        noteService.deleteNote(id, username);
        return ResponseEntity.noContent().build(); // Código 204 si la eliminación es exitosa
    }



    // Ruta para editar la nota
    @PutMapping("/edit/{id}")
    public ResponseEntity<Note> editNote(@PathVariable Long id,
                                         @RequestBody NoteRequestDTO noteDTO,
                                         Principal principal) {
        String username = principal.getName();  // Obtener el nombre de usuario desde el token
        Note updatedNote = noteService.updateNote(id, noteDTO, username);  // Llamar al servicio para editar la nota
        return ResponseEntity.ok(updatedNote);
    }



//Archive notes and unarchive


    @PutMapping("/archive/{id}")
    public ResponseEntity<Note> archiveNote(@PathVariable Long id, Principal principal) {
        String username = principal.getName();  // Obtener el username del usuario autenticado
        Note archivedNote = noteService.archiveNote(id, username);
        return ResponseEntity.ok(archivedNote);
    }

    @PutMapping("/unarchive/{id}")
    public ResponseEntity<Note> unarchiveNote(@PathVariable Long id, Principal principal) {
        String username = principal.getName();  // Obtener el username del usuario autenticado
        Note unarchivedNote = noteService.unarchiveNote(id, username);
        return ResponseEntity.ok(unarchivedNote);
    }







    @GetMapping("/archived")
    public ResponseEntity<List<NoteResponseDTO>> getArchivedNotes(Principal principal) {
        // Obtener las notas archivadas del usuario autenticado
        List<Note> archivedNotes = noteService.getArchivedNotes(principal.getName());

        // Convertir las notas en NoteResponseDTO
        List<NoteResponseDTO> response = archivedNotes.stream()
                .map(note -> NoteResponseDTO.builder()
                        .id(note.getId())
                        .title(note.getTitle())
                        .content(note.getContent())
                        .archived(note.isArchived())
                        .categoryName(note.getCategory() != null ? note.getCategory().getName() : null)
                        .build())
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/unarchived")
    public ResponseEntity<List<NoteResponseDTO>> getUnarchivedNotes(Principal principal) {
        // Obtener las notas no archivadas del usuario autenticado
        List<Note> unarchivedNotes = noteService.getUnarchivedNotes(principal.getName());

        // Convertir las notas en NoteResponseDTO
        List<NoteResponseDTO> response = unarchivedNotes.stream()
                .map(note -> NoteResponseDTO.builder()
                        .id(note.getId())
                        .title(note.getTitle())
                        .content(note.getContent())
                        .archived(note.isArchived())
                        .categoryName(note.getCategory() != null ? note.getCategory().getName() : null)
                        .build())
                .toList();

        return ResponseEntity.ok(response);
    }






    @GetMapping("/getcategory/{categoryName}")
    public ResponseEntity<List<NoteResponseDTO>> getNotesByCategory(
            @PathVariable String categoryName,
            Principal principal) {

        // Obtener el username del usuario autenticado
        String username = principal.getName();

        // Obtener las notas filtradas por categoría
        List<Note> notes = noteService.getNotesByCategory(categoryName, username);

        // Convertir las notas a NoteResponseDTO
        List<NoteResponseDTO> response = notes.stream()
                .map(note -> NoteResponseDTO.builder()
                        .id(note.getId())
                        .title(note.getTitle())
                        .content(note.getContent())
                        .archived(note.isArchived())
                        .categoryName(note.getCategory() != null ? note.getCategory().getName() : null)
                        .build())
                .toList();

        return ResponseEntity.ok(response);
    }

}



