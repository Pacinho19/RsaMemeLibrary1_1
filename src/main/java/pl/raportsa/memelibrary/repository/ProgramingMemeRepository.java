package pl.raportsa.memelibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.raportsa.memelibrary.entity.ProgramingMeme;

@Repository
public interface ProgramingMemeRepository extends JpaRepository<ProgramingMeme, Long> {
}