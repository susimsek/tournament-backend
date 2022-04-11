package io.github.susimsek.tournamentbackend.graphql.type

import java.time.ZonedDateTime


data class User (
    var id: String? = null,

    var username: String? = null,

    var email: String? = null,

    var createdDate: ZonedDateTime? = null
)