package io.github.susimsek.tournamentbackend.repository


import io.github.susimsek.tournamentbackend.domain.UserEntity
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*


interface UserRepository : MongoRepository<UserEntity, String> {
    fun findOneByUsername(username: String): Optional<UserEntity>

    fun existsOneByUsername(name: String): Boolean

    fun existsOneByEmail(email: String): Boolean

    fun findAllByIdIn(ids: MutableSet<String>): MutableSet<UserEntity>
}
