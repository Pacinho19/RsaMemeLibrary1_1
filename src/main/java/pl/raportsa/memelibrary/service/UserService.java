package pl.raportsa.memelibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.raportsa.memelibrary.entity.User;
import pl.raportsa.memelibrary.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User findByUsername(String name) {
        return userRepository.findByUsername(name).get();
    }
}
