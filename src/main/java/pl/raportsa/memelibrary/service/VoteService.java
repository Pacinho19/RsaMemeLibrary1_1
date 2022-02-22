package pl.raportsa.memelibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.raportsa.memelibrary.entity.Meme;
import pl.raportsa.memelibrary.entity.Vote;
import pl.raportsa.memelibrary.model.enums.VoteType;
import pl.raportsa.memelibrary.repository.VoteRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;

    public Vote findByUserAndMeme(Long userId, Long memeId) {
        return voteRepository.findByUserAndMeme(userId, memeId);
    }

    public Vote save(Vote vote) {
        return voteRepository.save(vote);
    }

    void setVotes(List<Meme> memes) {
        if (memes == null || memes.isEmpty()) return;

        List<Long> memeIds = memes.stream().map(Meme::getId).collect(Collectors.toList());
        List<Vote> votesForMemes = voteRepository.findByMemeIds(memeIds);
        if (votesForMemes == null) return;

        Map<Long, List<Vote>> votesMap = votesForMemes.stream().collect(Collectors.groupingBy(v -> v.getMeme().getId()));

        memes.forEach(m -> {
            List<Vote> votes = votesMap.get(m.getId());
            if (votes == null) return;
            m.setPositiveRateCount(getCountVotesByType(votes, VoteType.LIKE));
            m.setNegativeRateCount(getCountVotesByType(votes, VoteType.DISLIKE));
        });
    }

    private long getCountVotesByType(List<Vote> votes, VoteType voteType) {
        return votes.stream().filter(v -> v.getVoteType() == voteType).count();
    }

}