package pl.raportsa.memelibrary.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name ="Notifications")
@NoArgsConstructor
@Getter
@Setter
public class Notification {

    @Id
    @GenericGenerator(name = "notificationId", strategy = "increment")
    @GeneratedValue(generator = "notificationId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "meme_id")
    private Meme meme;

    private String text;

    private Date addDate;

    private Date readDate;


}
