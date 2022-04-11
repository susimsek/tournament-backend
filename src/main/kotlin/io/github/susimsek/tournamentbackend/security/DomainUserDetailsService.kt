package io.github.susimsek.tournamentbackend.security

import io.github.susimsek.tournamentbackend.domain.UserEntity
import io.github.susimsek.tournamentbackend.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import java.util.*


@Component("userDetailsService")
class DomainUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun loadUserByUsername(username: String): UserDetails {
        log.debug("Authenticating $username")

        val lowercaseUsername = username.lowercase(Locale.ENGLISH)
        return userRepository.findOneByUsername(lowercaseUsername)
            .map { createSpringSecurityUser(it) }
            .orElseThrow { UsernameNotFoundException("User $lowercaseUsername was not found in the database") }
    }

    private fun createSpringSecurityUser(user: UserEntity)
        : org.springframework.security.core.userdetails.User {
        return org.springframework.security.core.userdetails.User(
            user.id!!,
            user.password!!,
            mutableListOf()
        )
    }
}
