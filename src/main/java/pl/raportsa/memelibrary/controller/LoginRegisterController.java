package pl.raportsa.memelibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.raportsa.memelibrary.dto.UserDto;
import pl.raportsa.memelibrary.dto.mapper.UserDtoMapper;
import pl.raportsa.memelibrary.model.UserDetails;
import pl.raportsa.memelibrary.model.enums.RegisterMessage;
import pl.raportsa.memelibrary.service.UserDetailsService;
import pl.raportsa.memelibrary.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class LoginRegisterController {

    private final UserDetailsService userDetailsService;

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String finishRegister(Model model,
                                 UserDto userDto) {
        try {
            userDetailsService.loadUserByUsername(userDto.getUsername());
        } catch (UsernameNotFoundException ex) {
            userDetailsService.save(UserDtoMapper.parse(userDto));
            return "redirect:/login";
        }
        model.addAttribute("user", userDto);
        model.addAttribute("errorMessage", RegisterMessage.USERNAME_ERROR.getMessage());
        return "register";

    }

}
