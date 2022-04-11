package io.github.susimsek.tournamentbackend.service.mapper


import io.github.susimsek.tournamentbackend.domain.TournamentEntity
import io.github.susimsek.tournamentbackend.graphql.input.CreateTournamentInput
import io.github.susimsek.tournamentbackend.graphql.type.Tournament
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy


@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = [UserMapper::class])
interface TournamentMapper : EntityMapper<CreateTournamentInput, TournamentEntity, Tournament> {
}
