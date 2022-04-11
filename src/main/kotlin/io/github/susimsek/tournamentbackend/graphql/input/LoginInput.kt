package io.github.susimsek.tournamentbackend.graphql.input

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


data class LoginInput(
    @field:NotNull
    @field:Size(min = 1, max = 50)
    var username: String? = null,

    @field:NotNull
    @field:Size(min = 4, max = 100)
    var password: String? = null)
