package io.github.susimsek.tournamentbackend.graphql.resolver.mutation

import graphql.kickstart.tools.GraphQLMutationResolver
import io.github.susimsek.tournamentbackend.graphql.input.LoginInput
import io.github.susimsek.tournamentbackend.graphql.type.Token
import io.github.susimsek.tournamentbackend.service.TokenService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid

@Component
@Validated
class TokenMutation (
    private val tokenService: TokenService
) : GraphQLMutationResolver {

    fun login(@Valid @RequestBody credentials: LoginInput): Token {
        return tokenService.login(credentials)
    }
}