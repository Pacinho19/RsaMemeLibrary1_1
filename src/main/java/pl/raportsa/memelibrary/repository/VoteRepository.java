package pl.raportsa.memelibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.raportsa.memelibrary.entity.Vote;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query(nativeQuery = true,
            value = "select * from votes where user_id=:userId and meme_id=:memeId")
    Vote findByUserAndMeme(@Param("userId") long userId, @Param("memeId") long memeId);

    @Query(nativeQuery = true,
            value = "select * from votes where meme_id in :memeIds")
    List<Vote> findByMemeIds(@Param("memeIds") List<Long> memeIds);


    @Query(nativeQuery = true,
            value = "select * from votes where meme_id=:memeId")
    List<Vote> findByMemeId(@Param("memeId") long memeId);
}