package io.github.susimsek.tournamentbackend.security.jwt

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("security.authentication.token")
data class TokenProperties (
    var tokenValidityInSeconds: Long = 1800L,
    var base64Secret: String? = null
)