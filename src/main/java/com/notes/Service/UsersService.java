package com.notes.Service;

import com.notes.Models.UserModel;
import com.notes.Repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UsersService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    public UsersRepository usersRepository;
    public UserModel registerUser(String login, String password, String email) {
        if(login==null || password==null || email==null){
            return null;}
        else {
            UserModel user = new UserModel();
            user.setLogin(login);
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            System.out.println("Saving user: " + user);
            return usersRepository.save(user);

        }
    }
    public UserModel authenticateUser(String login, String rawPassword) {
        Optional<UserModel> userOpt = usersRepository.findByLogin(login);
        if (userOpt.isPresent()) {
            UserModel user = userOpt.get();


            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                return user;
            }
        }
        return null;
    }
    public UserModel findByLogin(String login) {
        return usersRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }
    public UserModel findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

}