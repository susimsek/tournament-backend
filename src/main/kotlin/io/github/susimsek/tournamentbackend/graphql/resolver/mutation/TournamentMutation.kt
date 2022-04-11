package io.github.susimsek.tournamentbackend.graphql.resolver.mutation

import graphql.kickstart.tools.GraphQLMutationResolver
import io.github.susimsek.tournamentbackend.graphql.input.CreateTournamentInput
import io.github.susimsek.tournamentbackend.graphql.input.UpdateTournamentInput
import io.github.susimsek.tournamentbackend.graphql.type.Tournament
import io.github.susimsek.tournamentbackend.security.getCurrentUserLogin
import io.github.susimsek.tournamentbackend.service.TournamentService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Component
@Validated
@PreAuthorize("isAuthenticated()")
class TournamentMutation (
    private val tournamentService: TournamentService
) : GraphQLMutationResolver {

    fun createTournament(@Valid @RequestBody tournament: CreateTournamentInput): Tournament {
        return tournamentService.createTournament(tournament)
    }

    fun updateTournament(@NotNull @Size(min=24, max = 24) id: String,
                         @Valid @RequestBody tournament: UpdateTournamentInput): Tournament {
        return tournamentService.updateTournament(id, tournament)
    }

    fun cancelTournament(@NotNull @Size(min=24, max = 24) id: String): Boolean {
        return tournamentService.cancelTournament(id)
    }

    fun joinTournament(@NotNull @Size(min=24, max = 24) id: String): Tournament {
        val currentUser = getCurrentUserLogin().get()
        return tournamentService.createTournamentPlayer(id, currentUser)
    }

    fun leaveTournament(@NotNull @Size(min=24, max = 24) id: String): Tournament {
        val currentUser = getCurrentUserLogin().get()
        return tournamentService.deleteTournamentPlayer(id, currentUser)
    }
}