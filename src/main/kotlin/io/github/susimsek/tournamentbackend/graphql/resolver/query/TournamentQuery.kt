package io.github.susimsek.tournamentbackend.graphql.resolver.query

import graphql.kickstart.tools.GraphQLQueryResolver
import io.github.susimsek.tournamentbackend.graphql.type.Tournament
import io.github.susimsek.tournamentbackend.security.getCurrentUserLogin
import io.github.susimsek.tournamentbackend.service.TournamentService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Component
@Validated
@PreAuthorize("isAuthenticated()")
class TournamentQuery (
    private val tournamentService: TournamentService
) : GraphQLQueryResolver {

    fun tournament(@NotNull @Size(min=24, max = 24) id: String): Tournament {
        return tournamentService.getTournament(id)
    }

    fun tournaments(): MutableList<Tournament> {
        return tournamentService.getTournaments()
    }

    fun myTournaments(): MutableList<Tournament> {
        val currentUser = getCurrentUserLogin().get()
        return tournamentService.getUserTournaments(currentUser)
    }
}