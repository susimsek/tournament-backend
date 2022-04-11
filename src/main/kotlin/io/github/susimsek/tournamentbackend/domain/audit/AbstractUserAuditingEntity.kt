package io.github.susimsek.tournamentbackend.domain.audit

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.mongodb.core.mapping.Field
import java.time.ZonedDateTime


abstract class AbstractUserAuditingEntity(

    createdDate: ZonedDateTime? = null,
    lastModifiedDate: ZonedDateTime? = null,

    @CreatedBy
    @Field("created_by")
    var createdBy: String? = null,

    @LastModifiedBy
    @Field("last_modified_by")
    var lastModifiedBy: String? = null
) : AbstractAuditingEntity(createdDate, lastModifiedDate)
