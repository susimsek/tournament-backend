package io.github.susimsek.tournamentbackend.graphql.input

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


data class CreateTournamentInput(
    @field:NotNull
    @field:Size(min = 1, max = 50)
    var name: String? = null,

    @field:NotNull
    var userIds: MutableSet<String> = mutableSetOf()
)
