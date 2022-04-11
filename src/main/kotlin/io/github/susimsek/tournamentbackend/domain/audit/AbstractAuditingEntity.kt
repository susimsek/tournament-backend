package io.github.susimsek.tournamentbackend.domain.audit

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Field
import java.time.ZonedDateTime


abstract class AbstractAuditingEntity (

    @CreatedDate
    @Field("created_date")
    var createdDate: ZonedDateTime? = null,

    @LastModifiedDate
    @Field("last_modified_date")
    var lastModifiedDate: ZonedDateTime? = null
)
