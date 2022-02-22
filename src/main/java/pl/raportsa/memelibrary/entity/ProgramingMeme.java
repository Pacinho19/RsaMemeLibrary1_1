package pl.raportsa.memelibrary.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "ProgramingMemes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProgramingMeme {

    @Id
    private Long id;
    @Column(name = "createdDate")
    private Date created;
    @Column(name = "modifiedDate")
    private Date modified;
    @Column(name = "url", length = 1000)
    private String image;
    private Date addDate;
}
