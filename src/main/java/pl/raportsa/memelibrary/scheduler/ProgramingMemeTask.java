package pl.raportsa.memelibrary.scheduler;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.raportsa.memelibrary.entity.ProgramingMeme;
import pl.raportsa.memelibrary.service.ProgramingMemeService;
import pl.raportsa.memelibrary.utils.ProgramingMemesApiConnector;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramingMemeTask {

    private final ProgramingMemesApiConnector programingMemesApiConnector;
    private final ProgramingMemeService programingMemeService;

    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void getMemesFromApi() {
//        List<ProgramingMeme> randomMemes = programingMemesApiConnector.getRandomMemes();
//        if (randomMemes == null) return;
//        programingMemeService.saveAll(randomMemes);
    }
}
