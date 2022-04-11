package io.github.susimsek.tournamentbackend.service

import io.github.susimsek.tournamentbackend.domain.UserEntity
import io.github.susimsek.tournamentbackend.exception.ResourceAlreadyExistsException
import io.github.susimsek.tournamentbackend.exception.ResourceNotFoundException
import io.github.susimsek.tournamentbackend.graphql.input.CreateUserInput
import io.github.susimsek.tournamentbackend.graphql.type.User
import io.github.susimsek.tournamentbackend.repository.UserRepository
import io.github.susimsek.tournamentbackend.service.mapper.UserMapper
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserService (
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userMapper: UserMapper
)  {

    fun createUser(input: CreateUserInput): User {
        if (userRepository.existsOneByUsername(input.username!!)) {
            throw ResourceAlreadyExistsException(
                String.format("Username %s already exists", input.username));
        }
        if (userRepository.existsOneByEmail(input.email!!)) {
            throw ResourceAlreadyExistsException(
                String.format("Email %s already exists", input.email));
        }
        input.password = passwordEncoder.encode(input.password);
        val user = userMapper.toEntity(input)
        return userMapper.toType(userRepository.save(user));
    }

    fun getUsers(): MutableList<User> {
        return userMapper.toType(userRepository.findAll());
    }

    fun getUser(id: String): User {
        val user = getUserEntity(id)
        return userMapper.toType(user);
    }

    fun getUserEntity(id: String): UserEntity {
        return userRepository.findById(id).orElseThrow { ResourceNotFoundException(
            String.format("User %s not found", id)) }
    }

    fun getUserMapByIdIn(ids: MutableSet<String>):
            Map<String?, User> {
        val users = getUserEntitiesByIdIn(ids, null)
        return users.associate { it.id to userMapper.toType(it) }
    }

    fun getUserEntitiesByIdIn(playerIds: MutableSet<String>, ownerId: String?): MutableSet<UserEntity> {
        var totalPlayerCount = playerIds.size
        if (ownerId != null) {
            totalPlayerCount++
            playerIds.add(ownerId)
        }
        val users = userRepository.findAllByIdIn(playerIds)
        if (users.isEmpty() || users.size != totalPlayerCount) {
            throw ResourceNotFoundException("Users not found");
        }
        return users
    }
}