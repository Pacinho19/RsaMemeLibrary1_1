package pl.raportsa.memelibrary.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.raportsa.memelibrary.entity.Comment;
import pl.raportsa.memelibrary.entity.Meme;
import pl.raportsa.memelibrary.entity.User;
import pl.raportsa.memelibrary.entity.Vote;
import pl.raportsa.memelibrary.model.enums.Category;
import pl.raportsa.memelibrary.model.enums.VoteType;
import pl.raportsa.memelibrary.model.pagination.Paged;
import pl.raportsa.memelibrary.service.CommentService;
import pl.raportsa.memelibrary.service.MemeService;
import pl.raportsa.memelibrary.service.UserService;
import pl.raportsa.memelibrary.service.VoteService;
import pl.raportsa.memelibrary.utils.FileUtils;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/rsameme/meme")
@RequiredArgsConstructor
public class MemeViewController {

    private final MemeService memeService;
    private final UserService userService;
    private final VoteService voteService;
    private final CommentService commentService;

    @GetMapping("/myMeme")
    public String userMeme(HttpSession session, Model model, Authentication authentication,
                           @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                           @RequestParam(value = "size", required = false, defaultValue = "5") int size) {

        if (pageNumber <= 0) pageNumber = 1;

        session.setAttribute("url", "/rsameme/meme/myMeme?pageNumber="+pageNumber);

        User user = userService.findByUsername(authentication.getName());
        Paged<Meme> memePage = memeService.findByUserWithVotes(user, pageNumber - 1, size);
        model.addAttribute("memes", memePage.getPage().getContent());
        model.addAttribute("memePage", memePage);
        model.addAttribute("url", "/rsameme/meme/myMeme");
        return "home";
    }

    @GetMapping("/add")
    public String addMeme(Model model) {
        model.addAttribute("meme", new Meme());
        model.addAttribute("categories", Category.values());
        return "addMeme";
    }

    @PostMapping("/add")
    public String addMeme(Meme meme, Authentication authentication, @RequestParam("memeFile") MultipartFile memeFile, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "home";
        }

        User user = userService.findByUsername(authentication.getName());
        String memeName = FileUtils.saveImage(user.getUsername(), memeFile);
        meme.setName(memeName);
        meme.setUser(user);
        Meme memeDb = memeService.save(meme);
        return "redirect:/rsameme/meme?id=" + memeDb.getId();
    }

    @GetMapping("/search")
    public String searchByTitle(HttpSession session,
                                @RequestParam(value = "title", required = false) String title, Model model,
                                @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                @RequestParam(value = "size", required = false, defaultValue = "5") int size) {

        if (title == null) title = (String) session.getAttribute("title");
        if (title == null || title.isEmpty()) return "redirect:/rsameme";

        session.setAttribute("title", title);
        session.setAttribute("url", "/rsameme/meme/search");

        if (pageNumber <= 0) pageNumber = 1;

        Paged<Meme> memePage = memeService.findByTitleWithVotes(title, pageNumber - 1, size);
        model.addAttribute("memes", memePage.getPage().getContent());
        model.addAttribute("memePage", memePage);
        model.addAttribute("url", "/rsameme/meme/search");
        return "home";
    }

    @PostMapping("/vote")
    public String vote(HttpSession session, @RequestParam("memeId") Long memeId, @RequestParam("type") int type, Authentication authentication) {
        Meme meme = memeService.findById(memeId);
        User user = userService.findByUsername(authentication.getName());
        Vote vote = voteService.findByUserAndMeme(user.getId(), memeId);

        if (vote == null) vote = new Vote(user, meme);
        vote.setVoteType(type == 1 ? VoteType.LIKE : VoteType.DISLIKE);
        voteService.save(vote);

        String url = (String) session.getAttribute("url");
        return url == null || url.isEmpty() ? "redirect:/rsameme" : "redirect:" + url;
    }

    @GetMapping
    public String comment(Model model, @RequestParam("id") long id) {
        Meme meme = memeService.findByIdWithVotes(id);
        List<Comment> comments = commentService.finByMemeId(id);
        model.addAttribute("meme", meme);
        model.addAttribute("comments", comments);
        return "commentMeme";
    }

    @GetMapping("/category/{category}")
    public String category(HttpSession session, Model model, @PathVariable("category") String category,
                           @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                           @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        Paged<Meme> memePage = memeService.findByCategory(category, pageNumber - 1, size);
        model.addAttribute("memes", memePage.getPage().getContent());
        model.addAttribute("memePage", memePage);
        model.addAttribute("url", "/rsameme/meme/category/" + category);
        session.setAttribute("url", "/rsameme/meme/category/" + category);

        return "home";
    }

    @PostMapping("/comment")
    public String comment(Authentication authentication, Model model, @RequestParam("memeId") long id, @RequestParam("commentText") String commentText) {
        User user = userService.findByUsername(authentication.getName());
        Meme meme = memeService.findById(id);
        commentService.save(new Comment(meme, user, commentText));
        return "redirect:/rsameme/meme?id=" + id;
    }

}