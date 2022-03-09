package pl.raportsa.memelibrary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.raportsa.memelibrary.dto.Stats;
import pl.raportsa.memelibrary.entity.Meme;
import pl.raportsa.memelibrary.entity.User;
import pl.raportsa.memelibrary.model.enums.Category;

import java.util.List;

@Repository
public interface MemeRepository extends JpaRepository<Meme, Long> {
    Page<Meme> findByTitle(String title, Pageable paging);

    Page<Meme> findByUser(User user, Pageable paging);

    List<Meme> findByUser(User user);

    Page<Meme> findByTitleContainingIgnoreCase(String title, Pageable paging);

    Page<Meme> findByCategory(Category category, Pageable paging);
}