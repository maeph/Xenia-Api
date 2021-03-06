package pl.jug.torun.xenia.model

import groovy.transform.ToString

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Entity
@ToString
class GiveAway {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id
    @OneToOne
    Prize prize
    @Column(nullable = false)
    int amount
    @OneToMany(cascade = CascadeType.ALL )
    List<Draw> draws
}
