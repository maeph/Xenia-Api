package pl.jug.torun.xenia.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.PrePersist
/**
 * Created by mephi_000 on 06.09.14.
 */
@Entity
class Prize {

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

    @PrePersist
    void initializeId() {
        if (id == null) {
            id = UUID.randomUUID()
        }
    }
}
