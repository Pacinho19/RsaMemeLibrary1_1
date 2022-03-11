package pl.raportsa.memelibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.raportsa.memelibrary.entity.Meme;
import pl.raportsa.memelibrary.entity.Notification;
import pl.raportsa.memelibrary.entity.Subscription;
import pl.raportsa.memelibrary.entity.User;
import pl.raportsa.memelibrary.repository.NotificationRepository;
import pl.raportsa.memelibrary.repository.SubscriptionRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final SubscriptionService subscriptionService;

    public List<Notification> findUnreadByUser(User user) {
        return notificationRepository.findAllByUserAndReadDateIsNull(user);
    }

    @Transactional
    public void read(Notification notification) {
        notification.setReadDate(new Date());
    }

    public void add(String text, User user, Meme meme) {
        Notification notification = new Notification();
        notification.setText(text);
        notification.setUser(user);
        notification.setAddDate(new Date());
        notification.setMeme(meme);
        notificationRepository.save(notification);
    }

    public void checkForNotifications(User user, String text, Meme meme) {
        List<Subscription> sub = subscriptionService.findByParent(user);
        sub.forEach(s -> {
            add(text, s.getChild(), meme);
        });
    }

    public Notification findById(long id) {
        return notificationRepository.getById(id);
    }

    @Transactional
    public void setAllAsRead(User user) {
        notificationRepository.findAllByUserAndReadDateIsNull(user)
                .forEach(n -> n.setReadDate(new Date()));
    }
}
