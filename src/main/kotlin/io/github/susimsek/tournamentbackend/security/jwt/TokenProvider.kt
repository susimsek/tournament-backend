package io.github.susimsek.tournamentbackend.security.jwt

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.util.ObjectUtils
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*
import javax.servlet.http.HttpServletRequest


private const val INVALID_JWT_TOKEN = "Invalid JWT token."

@Component
@EnableConfigurationProperties(TokenProperties::class)
class TokenProvider(
    private val tokenProperties: TokenProperties
) {

    private val log = LoggerFactory.getLogger(javaClass)

    private var key: Key? = null

    private var jwtParser: JwtParser? = null

    private var tokenValidityInMilliseconds: Long = 0

    init {
        val keyBytes: ByteArray
        var secret = tokenProperties.base64Secret
        keyBytes = Decoders.BASE64.decode(secret)
        this.key = Keys.hmacShaKeyFor(keyBytes)
        this.jwtParser = Jwts.parserBuilder().setSigningKey(key).build()
        this.tokenValidityInMilliseconds = 1000 * tokenProperties.tokenValidityInSeconds
    }

    fun createToken(authentication: Authentication): String {
        val now = Date().time
        val validity = Date(now + this.tokenValidityInMilliseconds)

        return Jwts.builder()
            .setSubject(authentication.name)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(validity)
            .compact()
    }

    fun getAuthentication(token: String, request: HttpServletRequest): Authentication {
        val claims = jwtParser?.parseClaimsJws(token)?.body

        val principal = User(claims?.subject, "",  mutableListOf())

        return JWTPreAuthenticationToken(principal,  WebAuthenticationDetailsSource().buildDetails(request))
    }

    fun validateToken(authToken: String): Boolean {
        try {
            jwtParser?.parseClaimsJws(authToken)
            return true
        } catch (e: ExpiredJwtException) {
            log.trace(INVALID_JWT_TOKEN, e)
        } catch (e: UnsupportedJwtException) {
            log.trace(INVALID_JWT_TOKEN, e)
        } catch (e: MalformedJwtException) {
            log.trace(INVALID_JWT_TOKEN, e)
        } catch (e: SignatureException) {
            log.trace(INVALID_JWT_TOKEN, e);
        } catch (e: IllegalArgumentException) {
            log.error("Token validation error {}", e.message)
        }
        return false
    }
}
