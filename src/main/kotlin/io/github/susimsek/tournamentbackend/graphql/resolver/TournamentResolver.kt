package io.github.susimsek.tournamentbackend.graphql.resolver

import graphql.kickstart.tools.GraphQLResolver
import io.github.susimsek.tournamentbackend.graphql.type.Tournament
import io.github.susimsek.tournamentbackend.graphql.type.User
import org.springframework.stereotype.Component

@Component
class TournamentResolver : GraphQLResolver<Tournament> {

    fun players(tournament: Tournament): MutableSet<User>? {
        return tournament.players
    }
}