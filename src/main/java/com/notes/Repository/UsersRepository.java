package com.notes.Repository;

import com.notes.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UserModel,Long> {
    Optional<UserModel> findByLogin(String login);
    public UserModel findByEmail(String email);

}