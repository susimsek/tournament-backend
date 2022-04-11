package io.github.susimsek.tournamentbackend.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import io.github.susimsek.tournamentbackend.domain.audit.AbstractAuditingEntity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.ZonedDateTime

/**
 * A user.
 */
@Document(collection = "user")
class UserEntity (
    @Id
    var id: String? = null,

    @Indexed
    var username: String? = null,

    @JsonIgnore
    var password: String? = null,

    @Indexed
    var email: String? = null,

    createdDate: ZonedDateTime? = null,
    lastModifiedDate: ZonedDateTime? = null
) : AbstractAuditingEntity(createdDate, lastModifiedDate) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UserEntity) return false
        return id != null && other.id != null && id == other.id
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}
