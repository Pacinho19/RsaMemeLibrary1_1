package pl.raportsa.memelibrary.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Comments")
@NoArgsConstructor
@Getter
@Setter
public class Comment {

    @Id
    @GenericGenerator(name = "commentId", strategy = "increment")
    @GeneratedValue(generator = "commentId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "MEME_ID")
    private Meme meme;

    private String text;

    private Date addDate;

    public Comment(Meme meme, User user, String text) {
        this.user = user;
        this.meme = meme;
        this.text = text;
        this.addDate = new Date();
    }
}