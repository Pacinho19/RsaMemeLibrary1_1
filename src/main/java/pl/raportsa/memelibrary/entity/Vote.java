package pl.raportsa.memelibrary.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import pl.raportsa.memelibrary.model.enums.VoteType;

import javax.persistence.*;

@Entity
@Table(name ="Votes")
@Getter
@Setter
@NoArgsConstructor
public class Vote {

    @Id
    @GenericGenerator(name = "voteId", strategy = "increment")
    @GeneratedValue(generator = "voteId")
    private int id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "MEME_ID")
    private Meme meme;

    @Enumerated(EnumType.STRING)
    private VoteType voteType;

    public Vote(User user, Meme meme) {
        this.user = user;
        this.meme = meme;
    }


}