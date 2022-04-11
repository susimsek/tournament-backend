package io.github.susimsek.tournamentbackend.graphql.resolver.mutation

import com.graphql.spring.boot.test.GraphQLTestTemplate
import io.github.susimsek.tournamentbackend.IntegrationTest
import io.github.susimsek.tournamentbackend.config.ClockConfig
import io.github.susimsek.tournamentbackend.utils.UserUtils.Companion.DEFAULT_USERNAME
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.io.IOException


@IntegrationTest
internal class UserMutationIT {

    @Autowired
    private lateinit var graphQLTestTemplate: GraphQLTestTemplate

    @Test
    @Throws(IOException::class)
    fun createUser() {
        val response = graphQLTestTemplate.postForResource("graphql/create-user.graphql")
        Assertions.assertThat(response.isOk).isTrue
        Assertions.assertThat(response["$.data.createUser.id"]).isNotNull
        Assertions.assertThat(response["$.data.createUser.username"]).isEqualTo(DEFAULT_USERNAME)
        Assertions.assertThat(response["$.data.createUser.createdDate"]).isEqualTo(ClockConfig.DEFAULT_DATE)
    }
}