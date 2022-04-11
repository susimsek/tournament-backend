@file:JvmName("SecurityUtils")

package io.github.susimsek.tournamentbackend.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import java.util.*


/**
 * Get the login of the current user.
 *
 * @return the login of the current user.
 */
fun getCurrentUserLogin(): Optional<String> =
    Optional.ofNullable(extractPrincipal(SecurityContextHolder.getContext().authentication))


fun extractPrincipal(authentication: Authentication?): String? {

    if(authentication == null) {
        return null
    }

    return when (val principal = authentication.principal) {
        is UserDetails -> principal.username
        is String -> principal
        else -> null
    }
}


/**
 * Get the JWT of the current user.
 *
 * @return the JWT of the current user.
 */
fun getCurrentUserJWT(): Optional<String> =
    Optional.ofNullable(SecurityContextHolder.getContext().authentication)
        .filter { it.credentials is String }
        .map { it.credentials as String }

/**
 * Check if a user is authenticated.
 *
 * @return true if the user is authenticated, false otherwise.
 */
fun isAuthenticated(): Boolean {
    val authentication = SecurityContextHolder.getContext().authentication

    if (authentication != null) {
        return true
    }

    return false
}