package pl.raportsa.memelibrary.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.raportsa.memelibrary.entity.Comment;
import pl.raportsa.memelibrary.entity.User;
import pl.raportsa.memelibrary.model.pagination.Paged;
import pl.raportsa.memelibrary.service.CommentService;
import pl.raportsa.memelibrary.service.SubscriptionService;
import pl.raportsa.memelibrary.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/rsameme/user")
@RequiredArgsConstructor
public class UserViewController {

    private final UserService userService;
    private final CommentService commentService;
    private final SubscriptionService subscriptionService;

    @GetMapping
    public String userProfile(Authentication authentication, HttpSession session, Model model, @RequestParam(value = "username") String username, @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                              @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        User user = userService.findByUsername(username);
        User child = userService.findByUsername(authentication.getName());

        model.addAttribute("user", user);
        model.addAttribute("stats", userService.getStats(user));
        Paged<Comment> commentPaged = commentService.findByUser(user, pageNumber - 1, size);
        model.addAttribute("comments", commentPaged.getPage().getContent());
        model.addAttribute("commentPage", commentPaged);
        model.addAttribute("url", "/rsameme/user?username=" + username);
        model.addAttribute("subscriptionId", subscriptionService.getSubscriptionId(user, child));
        session.setAttribute("url", "/rsameme/user?username=" + username);
        return "userProfile";
    }

    @PostMapping("/subscribe")
    public String subscribe(@RequestParam("userId") Long userId, @RequestParam("subscriptionId") int subscriptionId, Authentication authentication) {
        User userChild = userService.findByUsername(authentication.getName());
        User userParent = userService.findById(userId);
        if (subscriptionId > 0) {
            subscriptionService.deleteSubscription(subscriptionId);
        } else {
            subscriptionService.subscribe(userParent, userChild);
        }
        return "redirect:/rsameme/user?username=" + userParent.getUsername();
    }
}
