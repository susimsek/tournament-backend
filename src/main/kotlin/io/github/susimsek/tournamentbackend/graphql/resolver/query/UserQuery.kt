package io.github.susimsek.tournamentbackend.graphql.resolver.query

import graphql.kickstart.tools.GraphQLQueryResolver
import io.github.susimsek.tournamentbackend.graphql.type.User
import io.github.susimsek.tournamentbackend.security.getCurrentUserLogin
import io.github.susimsek.tournamentbackend.service.UserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Component
@Validated
@PreAuthorize("isAuthenticated()")
class UserQuery (
    private val userService: UserService
) : GraphQLQueryResolver {


    fun user(@NotNull @Size(min=24, max = 24) id: String): User {
        return userService.getUser(id)
    }

    fun currentUser(): User {
        val currentUser = getCurrentUserLogin().get()
        return userService.getUser(currentUser)
    }

    fun users(): MutableList<User> {
        return userService.getUsers()
    }
}