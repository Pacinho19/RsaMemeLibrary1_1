package pl.raportsa.memelibrary.model.enums;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum VoteType {
    LIKE(1),
    DISLIKE(2);

    private final int number;

    public static VoteType byNumber(int number) {
        return Arrays.stream(VoteType.values())
                .filter(vote -> vote.number==number)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid vote number!"));
    }
}