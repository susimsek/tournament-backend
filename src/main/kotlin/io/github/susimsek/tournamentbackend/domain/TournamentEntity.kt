package io.github.susimsek.tournamentbackend.domain

import io.github.susimsek.tournamentbackend.domain.audit.AbstractUserAuditingEntity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.ZonedDateTime

/**
 * A tournament.
 */
@Document(collection = "tournament")
class TournamentEntity  (
    @Id
    var id: String? = null,

    @Indexed
    var name: String? = null,

    @DBRef(lazy = true)
    var players: MutableSet<UserEntity>? = mutableSetOf(),

    createdBy: String? = null,
    createdDate: ZonedDateTime? = null,
    lastModifiedBy: String? = null,
    lastModifiedDate: ZonedDateTime? = null
) : AbstractUserAuditingEntity(createdDate, lastModifiedDate, createdBy, lastModifiedBy) {

    fun addPlayer(user: UserEntity) : TournamentEntity {
        this.players?.add(user)
        return this;
    }

    fun removePlayer(user: UserEntity) : TournamentEntity{
        this.players?.remove(user)
        return this;
    }
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TournamentEntity) return false
        return id != null && other.id != null && id == other.id
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}
