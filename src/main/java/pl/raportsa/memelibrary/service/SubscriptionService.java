package pl.raportsa.memelibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.raportsa.memelibrary.entity.Subscription;
import pl.raportsa.memelibrary.entity.User;
import pl.raportsa.memelibrary.repository.SubscriptionRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public void subscribe(User parent, User child) {
        if(Objects.equals(child.getId(), parent.getId())) return;
        Subscription subscription = new Subscription();
        subscription.setChild(child);
        subscription.setParent(parent);
        subscription.setAddDate(new Date());
        subscriptionRepository.save(subscription);
    }

    public int getSubscriptionId(User parent, User child) {
        if (Objects.equals(parent.getId(), child.getId())) return -1;
        Optional<Subscription> firstByParentAndChild = subscriptionRepository.findFirstByParentAndChild(parent, child);
        return firstByParentAndChild.map(subscription -> subscription.getId().intValue()).orElse(0);
    }

    public void deleteSubscription(int subscriptionId) {
        subscriptionRepository.deleteById((long) subscriptionId);
    }

    public List<Subscription> findByParent(User user){
        return subscriptionRepository.findAllByParent(user);
    }
}
