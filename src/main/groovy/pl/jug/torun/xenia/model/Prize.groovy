package pl.jug.torun.xenia.model

import javax.persistence.*
import javax.persistence.PrePersist


@Entity
class Prize {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(columnDefinition = "char(32)")
    String uuid

    @Column(nullable = false, unique = true)
    String name

    @Column(nullable = true)
    String producer

    @Column(nullable = true)
    String imageUrl

    @Column(nullable = true)
    String sponsorName

    @PrePersist
    void initializeUuid() {
        if (!uuid) {
            uuid = UUID.randomUUID().toString().replace('-', '')
        }
    }
}
