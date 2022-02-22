package pl.raportsa.memelibrary.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.raportsa.memelibrary.entity.Meme;
import pl.raportsa.memelibrary.entity.ProgramingMeme;
import pl.raportsa.memelibrary.model.pagination.Paged;
import pl.raportsa.memelibrary.service.ProgramingMemeService;

@Controller
@RequestMapping("/rsameme/meme/programing")
@RequiredArgsConstructor

public class ProgramingMemeViewController {

    private final ProgramingMemeService programingMemeService;

    @GetMapping
    public String allProgramingMemes(Model model,
                                     @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                     @RequestParam(value = "size", required = false, defaultValue = "5") int size) {

        if (pageNumber <= 0) pageNumber = 1;

        Paged<ProgramingMeme> memePage = programingMemeService.findAllPageable(pageNumber - 1, size);
        model.addAttribute("memes", memePage.getPage().getContent());
        model.addAttribute("memePage", memePage);
        model.addAttribute("url", "/rsameme/meme/programing");
        return "programingMeme";
    }
}
