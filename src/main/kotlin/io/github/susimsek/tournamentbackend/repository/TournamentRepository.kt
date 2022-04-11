package io.github.susimsek.tournamentbackend.repository


import io.github.susimsek.tournamentbackend.domain.TournamentEntity
import io.github.susimsek.tournamentbackend.domain.UserEntity
import org.springframework.data.mongodb.repository.MongoRepository


interface TournamentRepository : MongoRepository<TournamentEntity, String> {
    fun existsOneByName(name: String?): Boolean

    fun findAllByPlayers(user: UserEntity): MutableList<TournamentEntity>
}
