package pl.raportsa.memelibrary.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.raportsa.memelibrary.entity.Meme;
import pl.raportsa.memelibrary.entity.Notification;
import pl.raportsa.memelibrary.entity.User;
import pl.raportsa.memelibrary.model.pagination.Paged;
import pl.raportsa.memelibrary.service.MemeService;
import pl.raportsa.memelibrary.service.NotificationService;
import pl.raportsa.memelibrary.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeViewController {

    private final MemeService memeService;
    private final UserService userService;
    private final NotificationService notificationService;

    @GetMapping("/rsameme")
    public String home(Authentication authentication,
                       HttpSession session, Model model,
                       @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                       @RequestParam(value = "size", required = false, defaultValue = "5") int size) {

        if (pageNumber <= 0) pageNumber = 1;

        Paged<Meme> memePage = memeService.findAllWithVotes(pageNumber - 1, size);
        model.addAttribute("memes", memePage.getPage().getContent());
        model.addAttribute("memePage", memePage);
        model.addAttribute("url", "/rsameme");

        session.setAttribute("url", "/rsameme");

        User user = userService.findByUsername(authentication.getName());
        List<Notification> notifications = notificationService.findUnreadByUser(user);
        model.addAttribute("notifications", notifications);

        return "home";
    }

    @GetMapping
    public String home2() {
        return "redirect:/rsameme";
    }
}