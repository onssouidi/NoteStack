package com.notes.Controller;
import java.util.List;
import com.notes.Models.NoteModel;
import com.notes.Models.UserModel;
import com.notes.Service.NoteService;
import com.notes.Service.UsersService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/notes")
public class HomeController {

    private final NoteService noteService;
    private final UsersService usersService;

    @Autowired
    public HomeController(NoteService noteService, UsersService usersService) {
        this.noteService = noteService;
        this.usersService = usersService;
    }

    @ModelAttribute("currentUser")
    public UserModel getCurrentUser(Principal principal) {
        if (principal == null) return null;
        return usersService.findByLogin(principal.getName());
    }

    @GetMapping("/create")
    public String showCreateNoteForm(Model model) {
        model.addAttribute("note", new NoteModel());
        return "CreateNote";
    }

    @PostMapping("/create")
    public String createNote(@ModelAttribute @Valid NoteModel note,
                             @ModelAttribute("currentUser") UserModel user,
                             HttpSession session) {
        noteService.createNote(note, user);
        session.setAttribute("msg", "Note created successfully.");
        return "redirect:/notes/list?page=0";
    }

    @GetMapping("/list")
    public String listNotes(@RequestParam(defaultValue = "0") int page,
                            @ModelAttribute("currentUser") UserModel user,
                            Model model) {

        System.out.println("Current User: " + user);


        Page<NoteModel> notesPage = noteService.getNotesPerUser(user, PageRequest.of(page, 4, Sort.by("noteId").descending()));

        System.out.println("Fetched Notes: " + notesPage.getContent());

        model.addAttribute("notes", notesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", notesPage.getTotalPages());
        return "ViewNotes";
    }


    @GetMapping("/edit/{id}")
    public String editNote(@PathVariable Long id, @ModelAttribute("currentUser") UserModel user, Model model) {
        try {
            NoteModel note = noteService.getNoteByIdAndUser(id, user);
            model.addAttribute("note", note); 
            return "UpdateNote"; 
        } catch (Exception e) {
            // Handle exceptions
            return "redirect:/notes/list?page=0";
        }
    }

    @PostMapping("/update")
    public String updateNote(@ModelAttribute NoteModel note,
                             @ModelAttribute("currentUser") UserModel user,
                             HttpSession session) {
        try {
            noteService.updateNote(note.getNoteId(), note, user);
            session.setAttribute("msg", "Note updated successfully.");
        } catch (Exception e) {
            session.setAttribute("msg", "Erreur lors de la mise à jour.");
        }
        return "redirect:/notes/list?page=0";
    }

    @GetMapping("/delete/{id}")
    public String deleteNote(@PathVariable Long id,
                             @ModelAttribute("currentUser") UserModel user,
                             HttpSession session) {
        try {
            noteService.deleteNoteByIdAndUser(id, user);
            session.setAttribute("msg", "Note deleted successfully.");
        } catch (Exception e) {
            session.setAttribute("msg", "Erreur : " + e.getMessage());
        }
        return "redirect:/notes/list?page=0";
    }
    @GetMapping("/search")
    public String searchNotes(@RequestParam("keyword") String keyword, Model model) {
        List<NoteModel> notes = noteService.searchByKeyword(keyword);
        model.addAttribute("notes", notes);
        return "ViewNotes";
    }
}
