package pl.raportsa.memelibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.raportsa.memelibrary.entity.Subscription;
import pl.raportsa.memelibrary.entity.User;
import pl.raportsa.memelibrary.entity.Vote;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findFirstByParentAndChild(User parent, User child);

    List<Subscription> findAllByParent(User user);
}