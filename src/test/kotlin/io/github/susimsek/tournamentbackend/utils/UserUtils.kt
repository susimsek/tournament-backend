package io.github.susimsek.tournamentbackend.utils

import io.github.susimsek.tournamentbackend.domain.UserEntity
import io.github.susimsek.tournamentbackend.repository.UserRepository
import org.apache.commons.lang3.RandomStringUtils
import java.lang.UnsupportedOperationException

class UserUtils private constructor() {
    companion object {
        const val DEFAULT_ID = "6251422baa67d32f6b23aff4"
        const val DEFAULT_USERNAME = "test"
        const val DEFAULT_EMAIL = "johndoe@localhost"

        @JvmStatic
        fun createEntity(): UserEntity {
            return UserEntity(
                username = DEFAULT_USERNAME,
                password = RandomStringUtils.random(60),
                email = DEFAULT_EMAIL
            )
        }

        @JvmStatic
        fun initTestUser(userRepository: UserRepository): UserEntity {
            userRepository.deleteAll()
            return createEntity()
        }
    }

    init {
        throw UnsupportedOperationException("This is constant class")
    }
}