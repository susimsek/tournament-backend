package io.github.susimsek.tournamentbackend.graphql.resolver.mutation

import com.graphql.spring.boot.test.GraphQLTestTemplate
import io.github.susimsek.tournamentbackend.IntegrationTest
import io.github.susimsek.tournamentbackend.config.ClockConfig
import io.github.susimsek.tournamentbackend.utils.UserUtils
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.io.IOException

@IntegrationTest
internal class TournamentMutationIT {

    @Autowired
    private lateinit var graphQLTestTemplate: GraphQLTestTemplate

    @Test
    @Throws(IOException::class)
    fun createTournament() {
        val response = graphQLTestTemplate.postForResource("graphql/create-tournament.graphql")
        Assertions.assertThat(response.isOk).isTrue
        Assertions.assertThat(response["$.data.createUser.id"]).isNotNull
        Assertions.assertThat(response["$.data.createUser.username"]).isEqualTo(UserUtils.DEFAULT_USERNAME)
        Assertions.assertThat(response["$.data.createUser.createdDate"]).isEqualTo(ClockConfig.DEFAULT_DATE)
    }

}