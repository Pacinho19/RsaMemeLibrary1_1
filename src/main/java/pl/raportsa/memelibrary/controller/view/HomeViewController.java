package pl.raportsa.memelibrary.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.raportsa.memelibrary.entity.Meme;
import pl.raportsa.memelibrary.model.pagination.Paged;
import pl.raportsa.memelibrary.service.MemeService;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class HomeViewController {

    private final MemeService memeService;

    @GetMapping("/rsameme")
    public String home(HttpSession session, Model model,
                       @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                       @RequestParam(value = "size", required = false, defaultValue = "5") int size) {

        if (pageNumber <= 0) pageNumber = 1;

        Paged<Meme> memePage = memeService.findAllWithVotes(pageNumber - 1, size);
        model.addAttribute("memes", memePage.getPage().getContent());
        model.addAttribute("memePage", memePage);
        model.addAttribute("url", "/rsameme");

        session.setAttribute("url", "/rsameme");


        return "home";
    }

    @GetMapping
    public String home2() {
        return "redirect:/rsameme";
    }
}