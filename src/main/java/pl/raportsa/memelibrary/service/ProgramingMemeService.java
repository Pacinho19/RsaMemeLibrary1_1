package pl.raportsa.memelibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.raportsa.memelibrary.entity.ProgramingMeme;
import pl.raportsa.memelibrary.model.pagination.Paged;
import pl.raportsa.memelibrary.model.pagination.Paging;
import pl.raportsa.memelibrary.repository.ProgramingMemeRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramingMemeService {

    private final ProgramingMemeRepository programingMemeRepository;

    public void saveAll(List<ProgramingMeme> programingMemes){
        programingMemes.forEach(pm -> pm.setAddDate(new Date()));
        programingMemeRepository.saveAll(programingMemes);
    }

    public Paged<ProgramingMeme> findAllPageable(int pageNumber, int pageSize){
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by("addDate").descending());
        Page<ProgramingMeme> memes = programingMemeRepository.findAll(paging);
        return new Paged<>(memes, Paging.of(memes.getTotalPages(), pageNumber + 1, pageSize));
    }
}
