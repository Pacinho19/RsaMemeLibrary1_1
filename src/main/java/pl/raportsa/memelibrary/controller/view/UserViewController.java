package pl.raportsa.memelibrary.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.raportsa.memelibrary.dto.Stats;
import pl.raportsa.memelibrary.entity.Comment;
import pl.raportsa.memelibrary.entity.Meme;
import pl.raportsa.memelibrary.entity.User;
import pl.raportsa.memelibrary.model.pagination.Paged;
import pl.raportsa.memelibrary.service.CommentService;
import pl.raportsa.memelibrary.service.MemeService;
import pl.raportsa.memelibrary.service.UserService;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.List;

@Controller
@RequestMapping("/rsameme/user")
@RequiredArgsConstructor
public class UserViewController {

    private final UserService userService;
    private final CommentService commentService;

    @GetMapping
    public String userProfile(HttpSession session, Model model, @RequestParam(value = "username") String username, @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                              @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        User user = userService.findByUsername(username);
        model.addAttribute("user",user);
        model.addAttribute("stats",userService.getStats(user));
        Paged<Comment> commentPaged = commentService.findByUser(user, pageNumber - 1, size);
        model.addAttribute("comments", commentPaged.getPage().getContent());
        model.addAttribute("commentPage", commentPaged);
        model.addAttribute("url", "/rsameme/user?username=" + username);
        session.setAttribute("url", "/rsameme/user?username=" + username);
        return "userProfile";
    }
}
