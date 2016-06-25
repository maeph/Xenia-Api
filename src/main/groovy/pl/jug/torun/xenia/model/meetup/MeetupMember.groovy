package pl.jug.torun.xenia.model.meetup

import pl.jug.torun.xenia.model.Member

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.OneToOne


@Entity
class MeetupMember {
    @Id
    long id
    @OneToOne(cascade = CascadeType.ALL)
    Member member
    
}
