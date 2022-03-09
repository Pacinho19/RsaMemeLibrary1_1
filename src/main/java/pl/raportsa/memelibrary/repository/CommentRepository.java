package pl.raportsa.memelibrary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.raportsa.memelibrary.entity.Comment;
import pl.raportsa.memelibrary.entity.User;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(nativeQuery = true,
            value = "select * from comments where MEME_ID=:memeId")
    List<Comment> findByMemeId(@Param("memeId") long memeId);

    @Query(nativeQuery = true,
            value = "select  * from comments where MEME_ID in :memeIds")
    List<Comment> findByMemeIds(@Param("memeIds") List<Long> memeIds);

    @Query(nativeQuery = true,
            value = "select  * from comments where USER_ID=:userId")
    Page<Comment> findByUser(@Param("userId") long userId, Pageable paging);
}