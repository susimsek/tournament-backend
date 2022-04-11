package io.github.susimsek.tournamentbackend.security.jwt

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken

class JWTPreAuthenticationToken(principal: UserDetails, details: WebAuthenticationDetails?) :
    PreAuthenticatedAuthenticationToken(principal, null, principal.authorities) {
    override fun getCredentials(): Any? {
        return null
    }

    init {
        super.setDetails(details)
    }
}