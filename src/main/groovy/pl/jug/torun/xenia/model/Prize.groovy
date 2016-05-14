package pl.jug.torun.xenia.model

import javax.persistence.*


@Entity
class Prize {

    @Id
    String id;

    @Column(nullable = false, unique = true)
    String name

    @Column(nullable = true)
    String producer

    @Column(nullable = true)
    String imageUrl

    @Column(nullable = true)
    String sponsorName

    @PrePersist
    void initializeId() {
        if (id == null) {
            id = UUID.randomUUID()
        }
    }
}
