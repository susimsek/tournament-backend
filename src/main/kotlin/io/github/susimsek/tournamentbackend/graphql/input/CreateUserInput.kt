package io.github.susimsek.tournamentbackend.graphql.input

import org.springframework.data.mongodb.core.index.Indexed
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


data class CreateUserInput(
    @field:NotNull
    @field:Size(min = 1, max = 50)
    var username: String? = null,

    @field:NotNull
    @field:Size(min = 4, max = 100)
    var password: String? = null,

    @field:Email
    @field:Size(min = 5, max = 254)
    @Indexed
    var email: String? = null
)
