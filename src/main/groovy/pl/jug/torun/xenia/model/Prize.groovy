package pl.jug.torun.xenia.model

import org.hibernate.annotations.GenericGenerator

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

/**
 * Created by mephi_000 on 06.09.14.
 */
@Entity
class Prize {

    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "char(32)")
    @Id
    String id;

    @Column(nullable = false)
    String name

    @Column(nullable = true)
    String producer

    @Column(nullable = true)
    String imageUrl

    @Column(nullable = true)
    String sponsorName
}
