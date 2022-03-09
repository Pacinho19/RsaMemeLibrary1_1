package pl.raportsa.memelibrary.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import pl.raportsa.memelibrary.model.enums.Category;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "Memes")
@Getter
@Setter
@NoArgsConstructor
public class Meme {

    @Id
    @GenericGenerator(name = "memeId", strategy = "increment")
    @GeneratedValue(generator = "memeId")
    private Long id;

    @Size(min = 2)
    @Column(length = 50, nullable = false)
    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Date addDate;

    @Transient
    private long positiveRateCount = 0L;

    @Transient
    private long negativeRateCount = 0L;

    @Transient
    private Comment lastComment;

}