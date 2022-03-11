package pl.raportsa.memelibrary.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
public enum RegisterMessage {
    USERNAME_ERROR("That username already exist.", 1),
    EMAIL_ERROR("That email already exist.", 2),
    OTHER_ERROR("Unidentified error.", 3);

    @Getter
    private String message;
    private int id;

    public static RegisterMessage byId(int id) {
        return Arrays.stream(RegisterMessage.values())
                .filter(registerMessage -> registerMessage.id == id)
                .findFirst()
                .orElse(OTHER_ERROR);
    }
}
