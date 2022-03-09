package pl.raportsa.memelibrary.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name ="Subscriptions")
@NoArgsConstructor
@Getter
@Setter
public class Subscription {

    @Id
    @GenericGenerator(name = "subscriptionId", strategy = "increment")
    @GeneratedValue(generator = "subscriptionId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private User parent;

    @ManyToOne
    @JoinColumn(name = "child_id")
    private User child;

    private Date addDate;

}
