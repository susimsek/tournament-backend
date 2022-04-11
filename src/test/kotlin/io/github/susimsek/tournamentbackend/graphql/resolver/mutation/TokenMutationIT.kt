package io.github.susimsek.tournamentbackend.graphql.resolver.mutation

import com.graphql.spring.boot.test.GraphQLTestTemplate
import io.github.susimsek.tournamentbackend.IntegrationTest
import io.github.susimsek.tournamentbackend.utils.UserUtils.Companion.DEFAULT_EMAIL
import io.github.susimsek.tournamentbackend.utils.UserUtils.Companion.DEFAULT_USERNAME
import io.github.susimsek.tournamentbackend.domain.UserEntity
import io.github.susimsek.tournamentbackend.repository.UserRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import java.io.IOException

private const val DEFAULT_PASSWORD = "1234"

@IntegrationTest
internal class TokenMutationIT {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var graphQLTestTemplate: GraphQLTestTemplate

    @Test
    @Throws(IOException::class)
    fun login() {
        val user = UserEntity(
            username = DEFAULT_USERNAME,
            password = passwordEncoder.encode(DEFAULT_PASSWORD),
            email = DEFAULT_EMAIL
        )
        userRepository.save(user)

        val response = graphQLTestTemplate.postForResource("graphql/login.graphql")
        Assertions.assertThat(response.isOk).isTrue
        Assertions.assertThat(response["$.data.login.token"]).isNotNull
        Assertions.assertThat(response["$.data.login.token"]).isNotEmpty
    }
}