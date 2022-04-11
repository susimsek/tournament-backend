package io.github.susimsek.tournamentbackend.graphql.resolver.mutation

import graphql.kickstart.tools.GraphQLMutationResolver
import io.github.susimsek.tournamentbackend.graphql.input.CreateUserInput
import io.github.susimsek.tournamentbackend.graphql.type.User
import io.github.susimsek.tournamentbackend.service.UserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid

@Component
@Validated
class UserMutation (
    private val userService: UserService
) : GraphQLMutationResolver {

    fun createUser(@Valid @RequestBody user: CreateUserInput): User {
        return userService.createUser(user)
    }
}