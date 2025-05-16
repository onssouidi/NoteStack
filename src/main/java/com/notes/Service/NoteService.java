package com.notes.Service;

import com.notes.Models.NoteModel;
import com.notes.Models.UserModel;
import com.notes.Repository.NoteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void createNote(NoteModel note, UserModel user) {
        note.setUser(user);
        noteRepository.save(note);
    }

    public Page<NoteModel> getNotesPerUser(UserModel user, Pageable pageable) {
        return noteRepository.findByUser(user.getId(), pageable);
    }

    public NoteModel getNoteByIdAndUser(Long id, UserModel user) {
        NoteModel note = noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note introuvable"));
        if (!note.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Accès interdit à cette note.");
        }
        return note;
    }

    public void updateNote(Long noteId, NoteModel modifiedNote, UserModel user) {
        NoteModel note = getNoteByIdAndUser(noteId, user);
        note.setTitle(modifiedNote.getTitle());
        note.setContent(modifiedNote.getContent());
        noteRepository.save(note);
    }

    public void deleteNoteByIdAndUser(Long id, UserModel user) {
        NoteModel note = getNoteByIdAndUser(id, user);
        noteRepository.delete(note);
    }
    public List<NoteModel> searchByKeyword(String keyword) {
        return noteRepository.searchByKeyword(keyword);
    }
}



