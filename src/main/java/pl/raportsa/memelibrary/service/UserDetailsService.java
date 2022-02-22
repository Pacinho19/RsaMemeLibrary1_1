/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.raportsa.memelibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.raportsa.memelibrary.config.CryptoConfig;
import pl.raportsa.memelibrary.dto.UserDto;
import pl.raportsa.memelibrary.dto.mapper.UserDtoMapper;
import pl.raportsa.memelibrary.entity.User;
import pl.raportsa.memelibrary.model.UserDetails;
import pl.raportsa.memelibrary.repository.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author pojdana
 */
@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;
    private final CryptoConfig cryptoConfig;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(userName);
        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
        return user.map(UserDetails::new).get();
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User save(UserDto userDto) {
        User user = UserDtoMapper.parse(userDto);
        user.setPassword(cryptoConfig.encoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User save(User user) {
        user.setPassword(cryptoConfig.encoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getById(long id) {
        return userRepository.findById(id);
    }

}