package pl.raportsa.memelibrary.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.raportsa.memelibrary.entity.User;
import pl.raportsa.memelibrary.model.enums.Role;
import pl.raportsa.memelibrary.service.UserDetailsService;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class Initializer {

    private final UserDetailsService userDetailsService;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
//        initUsers();
    }

    private void initUsers() {
        Arrays.asList(
                new User("pojdana","admin", 1, Role.ROLE_ADMIN)
        ).forEach(userDetailsService::save);
    }

}