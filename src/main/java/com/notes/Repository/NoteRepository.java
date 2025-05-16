package com.notes.Repository;

import com.notes.Models.NoteModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;

import java.util.List;


public interface NoteRepository extends JpaRepository<NoteModel,Long> {
    @Query("SELECT n FROM NoteModel n WHERE n.user.id = :uid")
    Page<NoteModel> findByUser(@Param("uid") Long uid, Pageable pageable);
    @Query("SELECT n FROM NoteModel n WHERE LOWER(n.Title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(n.Content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<NoteModel> searchByKeyword(@Param("keyword") String keyword);
}