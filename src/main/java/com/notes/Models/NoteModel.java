package com.notes.Models;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name="notes")
public class NoteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteId;
    private String Title;
    @Column(columnDefinition = "TEXT")
    private String Content;
    @Temporal(TemporalType.DATE)
    @CreatedDate
    @Column(name="DateCreated",nullable=false,updatable=false)
    private String DateCreated;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "users_id",nullable= false)
    private UserModel user;

    public NoteModel() {
        this.DateCreated = String.valueOf(LocalDateTime.now());
    }
    public NoteModel(String titre, String contenu, UserModel user) {
        this.Title = titre;
        this.Content = contenu;
        this.DateCreated = String.valueOf(LocalDateTime.now());
        this.user = user;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long nId) {
        noteId = nId;}

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(String dateCreated) {
        DateCreated = dateCreated;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel u) {
        user = u;
    }
}
