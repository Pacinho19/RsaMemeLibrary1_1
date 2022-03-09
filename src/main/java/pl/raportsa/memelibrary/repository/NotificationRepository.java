package pl.raportsa.memelibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.raportsa.memelibrary.entity.Notification;
import pl.raportsa.memelibrary.entity.Subscription;
import pl.raportsa.memelibrary.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByUserAndReadDateIsNull(User user);
}