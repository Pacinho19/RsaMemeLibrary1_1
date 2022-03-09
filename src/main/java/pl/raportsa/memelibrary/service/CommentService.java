package pl.raportsa.memelibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.raportsa.memelibrary.entity.Comment;
import pl.raportsa.memelibrary.entity.Meme;
import pl.raportsa.memelibrary.entity.User;
import pl.raportsa.memelibrary.model.pagination.Paged;
import pl.raportsa.memelibrary.model.pagination.Paging;
import pl.raportsa.memelibrary.repository.CommentRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<Comment> finByMemeId(Long memeId) {
        return commentRepository.findByMemeId(memeId);
    }

    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    public List<Comment> finByMemeIds(List<Long> memeIds) {
        return commentRepository.findByMemeIds(memeIds);
    }

    public void setLastComment(List<Meme> memes) {
        if (memes == null || memes.isEmpty()) return;

        List<Comment> comments = finByMemeIds(memes.stream().map(Meme::getId).collect(Collectors.toList()));
        Map<Long, List<Comment>> commentByMeme = comments.stream().collect(Collectors.groupingBy(c -> c.getMeme().getId()));
        memes.forEach(m -> {
            List<Comment> comments1 = commentByMeme.get(m.getId());
            if (comments1 == null || comments1.isEmpty()) return;
            Comment comment = comments1.stream().max(Comparator.comparing(Comment::getAddDate)).get();
            m.setLastComment(comment);
        });
    }

    public Paged<Comment> findByUser(User user, int pageNumber, int pageSize ){
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by("id").descending());
        Page<Comment> comments = commentRepository.findByUser(user.getId(), paging);
        return new Paged<>(comments, Paging.of(comments.getTotalPages(), pageNumber + 1, pageSize));
    }
}