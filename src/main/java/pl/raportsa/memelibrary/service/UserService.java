package pl.raportsa.memelibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.raportsa.memelibrary.dto.Stats;
import pl.raportsa.memelibrary.entity.Meme;
import pl.raportsa.memelibrary.entity.User;
import pl.raportsa.memelibrary.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MemeService memeService;


    public User findByUsername(String name) {
        return userRepository.findByUsername(name).get();
    }

    public Stats getStats(User user) {
        List<Meme> memes = memeService.findByUserWithVotes(user);
        Stats s = new Stats();
        s.setMemeCount(memes.size());
        s.setPositiveVotesCount(memes.stream().map(Meme::getPositiveRateCount).reduce(0L, Long::sum).intValue());
        s.setNegativeVotesCount(memes.stream().map(Meme::getNegativeRateCount).reduce(0L, Long::sum).intValue());
        return s;
    }
}
