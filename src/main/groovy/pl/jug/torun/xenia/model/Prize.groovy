package pl.jug.torun.xenia.model

import org.hibernate.annotations.GenericGenerator

import javax.persistence.*


@Entity
class Prize {

    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "char(32)")
    @Id
    String id

    @Column(nullable = false, unique = true)
    String name

    @Column(nullable = true)
    String producer

    @Column(nullable = true)
    String imageUrl

    @Column(nullable = true)
    String sponsorName

}
