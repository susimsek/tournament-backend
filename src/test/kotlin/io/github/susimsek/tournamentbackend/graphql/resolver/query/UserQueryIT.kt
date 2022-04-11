package io.github.susimsek.tournamentbackend.graphql.resolver.query

import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.fasterxml.jackson.databind.node.ObjectNode
import com.graphql.spring.boot.test.GraphQLTestTemplate
import io.github.susimsek.tournamentbackend.IntegrationTest
import io.github.susimsek.tournamentbackend.config.ClockConfig
import io.github.susimsek.tournamentbackend.config.ClockConfig.Companion.DEFAULT_DATE
import io.github.susimsek.tournamentbackend.domain.UserEntity
import io.github.susimsek.tournamentbackend.graphql.type.User
import io.github.susimsek.tournamentbackend.repository.UserRepository
import io.github.susimsek.tournamentbackend.service.UserService
import io.github.susimsek.tournamentbackend.service.mapper.UserMapper
import io.github.susimsek.tournamentbackend.utils.UserUtils.Companion.DEFAULT_EMAIL
import io.github.susimsek.tournamentbackend.utils.UserUtils.Companion.DEFAULT_ID
import io.github.susimsek.tournamentbackend.utils.UserUtils.Companion.DEFAULT_USERNAME
import io.github.susimsek.tournamentbackend.utils.UserUtils.Companion.createEntity
import io.github.susimsek.tournamentbackend.utils.UserUtils.Companion.initTestUser
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doReturn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import java.io.IOException


@IntegrationTest
internal class UserQueryIT {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var graphQLTestTemplate: GraphQLTestTemplate

    private lateinit var userEntity: UserEntity

    @BeforeEach
    fun initTest() {
        userEntity = initTestUser(userRepository)
    }

    @Test
    @Throws(IOException::class)
    fun getUser() {
        userRepository.save(userEntity)

        val variables = ObjectNode(JsonNodeFactory.instance)
        variables.put("id", userEntity.id)

        val response = graphQLTestTemplate.perform("graphql/get-user.graphql",
            variables
        )
        Assertions.assertThat(response.isOk).isTrue
        Assertions.assertThat(response["$.data.user.id"]).isNotNull
        Assertions.assertThat(response["$.data.user.username"]).isEqualTo(DEFAULT_USERNAME)
        Assertions.assertThat(response["$.data.user.email"]).isEqualTo(DEFAULT_EMAIL)
        Assertions.assertThat(response["$.data.user.createdDate"]).isEqualTo(DEFAULT_DATE)
    }

    @Test
    @Throws(IOException::class)
    fun getUsers() {
        userRepository.save(userEntity)

        val response = graphQLTestTemplate.postForResource("graphql/get-users.graphql")
        Assertions.assertThat(response.isOk).isTrue
        Assertions.assertThat(response["$.data.users[0].id"]).isNotNull
        Assertions.assertThat(response["$.data.users[0].username"]).isEqualTo(DEFAULT_USERNAME)
        Assertions.assertThat(response["$.data.users[0].email"]).isEqualTo(DEFAULT_EMAIL)
        Assertions.assertThat(response["$.data.users[0].createdDate"]).isEqualTo(DEFAULT_DATE)
    }
}