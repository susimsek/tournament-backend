package io.github.susimsek.tournamentbackend.graphql.type

import java.time.ZonedDateTime


data class Tournament  (
    var id: String? = null,

    var name: String? = null,

    var players: MutableSet<User>? = mutableSetOf(),
    var owner: User? = null,

    var createdDate: ZonedDateTime? = null
)