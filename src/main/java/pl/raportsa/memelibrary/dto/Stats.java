package pl.raportsa.memelibrary.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class Stats {

    private int memeCount;
    private int positiveVotesCount;
    private int negativeVotesCount;

}
