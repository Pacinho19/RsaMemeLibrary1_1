package pl.raportsa.memelibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.raportsa.memelibrary.dto.UserDto;
import pl.raportsa.memelibrary.entity.User;
import pl.raportsa.memelibrary.service.UserDetailsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserDetailsService userDetailsService;

    @PostMapping
    public User add(@RequestBody UserDto user){
        return userDetailsService.save(user);
    }
}
