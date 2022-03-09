package pl.raportsa.memelibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.raportsa.memelibrary.entity.Meme;
import pl.raportsa.memelibrary.entity.User;
import pl.raportsa.memelibrary.model.enums.Category;
import pl.raportsa.memelibrary.model.pagination.Paged;
import pl.raportsa.memelibrary.model.pagination.Paging;
import pl.raportsa.memelibrary.repository.MemeRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemeService {

    private final MemeRepository memeRepository;
    private final VoteService voteService;
    private final CommentService commentService;

    public List<Meme> findAll() {
        return memeRepository.findAll();
    }

    public Paged<Meme> findAllWithVotes(int pageNumber, int pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by("addDate").descending());
        Page<Meme> memes = memeRepository.findAll(paging);
        voteService.setVotes(memes.getContent());
        commentService.setLastComment(memes.getContent());
        return new Paged<>(memes, Paging.of(memes.getTotalPages(), pageNumber + 1, pageSize));
    }

    public Meme save(Meme meme) {
        if (meme.getId() == null) meme.setAddDate(new Date());
        return memeRepository.save(meme);
    }

    public Paged<Meme> findByTitle(String title, int pageNumber, int pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by("addDate").descending());
        Page<Meme> memes = memeRepository.findByTitle(title, paging);
        return new Paged<>(memes, Paging.of(memes.getTotalPages(), pageNumber + 1, pageSize));
    }

    public Paged<Meme> findByTitleWithVotes(String title, int pageNumber, int pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by("addDate").descending());
        Page<Meme> memes = memeRepository.findByTitleContainingIgnoreCase(title, paging);
        voteService.setVotes(memes.getContent());
        commentService.setLastComment(memes.getContent());
        return new Paged<>(memes, Paging.of(memes.getTotalPages(), pageNumber + 1, pageSize));
    }

    public Meme findById(Long memeId) {
        return memeRepository.getById(memeId);
    }

    public Meme findByIdWithVotes(long id) {
        Meme meme = memeRepository.getById(id);
        voteService.setVotes(Collections.singletonList(meme));
        return meme;
    }

    public List<Meme> findByUserWithVotes(User user) {
        List<Meme> memes = memeRepository.findByUser(user);
        voteService.setVotes(memes);
        return memes;
    }

    public Paged<Meme> findByUserWithVotes(User user, int pageNumber, int pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by("addDate").descending());
        Page<Meme> memes = memeRepository.findByUser(user, paging);
        voteService.setVotes(memes.getContent());
        commentService.setLastComment(memes.getContent());
        return new Paged<>(memes, Paging.of(memes.getTotalPages(), pageNumber + 1, pageSize));
    }

    public Paged<Meme> findByCategory(String category, int pageNumber, int pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by("addDate").descending());
        Page<Meme> memes = memeRepository.findByCategory(Category.valueOf(category), paging);
        voteService.setVotes(memes.getContent());
        commentService.setLastComment(memes.getContent());
        return new Paged<>(memes, Paging.of(memes.getTotalPages(), pageNumber + 1, pageSize));
    }
}