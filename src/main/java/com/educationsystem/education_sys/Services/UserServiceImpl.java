package com.educationsystem.education_sys.Services;
import com.educationsystem.education_sys.repositories.UserRepository;
import com.educationsystem.education_sys.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    @Autowired
    private final UserRepository userRepository;
    @Override
    public User getUser(Long id){
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User createUser(User user){
        if (userRepository.findByUsername(user.getUsername()) == null  || userRepository.findByEmail(user.getEmail()) == null ) {
            throw new IllegalArgumentException("Username or email already exists");
        }
        String hashedPassword = BCrypt.hashpw(user.getPassword(),BCrypt.gensalt());
        return userRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email){
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

}
