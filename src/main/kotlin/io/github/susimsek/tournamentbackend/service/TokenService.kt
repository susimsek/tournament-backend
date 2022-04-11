package io.github.susimsek.tournamentbackend.service

import io.github.susimsek.tournamentbackend.graphql.input.LoginInput
import io.github.susimsek.tournamentbackend.graphql.type.Token
import io.github.susimsek.tournamentbackend.security.jwt.TokenProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class TokenService (
    private val tokenProvider: TokenProvider,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder
)  {

    fun login(credentials: LoginInput): Token {

        val authenticationToken = UsernamePasswordAuthenticationToken(credentials.username, credentials.password)
        
        val authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken)
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = tokenProvider.createToken(authentication)
        return Token(jwt)
    }
}