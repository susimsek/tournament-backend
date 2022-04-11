package io.github.susimsek.tournamentbackend.service

import io.github.susimsek.tournamentbackend.domain.TournamentEntity
import io.github.susimsek.tournamentbackend.exception.ResourceAlreadyExistsException
import io.github.susimsek.tournamentbackend.exception.ResourceNotFoundException
import io.github.susimsek.tournamentbackend.graphql.input.CreateTournamentInput
import io.github.susimsek.tournamentbackend.graphql.input.UpdateTournamentInput
import io.github.susimsek.tournamentbackend.graphql.type.Tournament
import io.github.susimsek.tournamentbackend.repository.TournamentRepository
import io.github.susimsek.tournamentbackend.security.getCurrentUserLogin
import io.github.susimsek.tournamentbackend.service.mapper.TournamentMapper
import io.github.susimsek.tournamentbackend.service.mapper.UserMapper
import org.springframework.stereotype.Component

@Component
class TournamentService (
    private val tournamentRepository: TournamentRepository,
    private val userService: UserService,
    private val tournamentMapper: TournamentMapper,
    private val userMapper: UserMapper
)  {

    fun createTournament(input: CreateTournamentInput): Tournament {

        checkName(input.name)

        val users = userService.getUserEntitiesByIdIn(input.userIds,
                getCurrentUserLogin().get())

        val user = TournamentEntity(
            name = input.name,
            players = users
        )
        val entity = tournamentRepository.save(user)
        val tournament = tournamentMapper.toType(entity)
        tournament.owner = userService.getUser(entity.createdBy!!)
        return tournament
    }

    fun getTournaments(): MutableList<Tournament> {
        val tournamentEntities = tournamentRepository.findAll()
        val ownerIds = tournamentEntities.map {
                tournament -> tournament.createdBy!!
        }.toMutableSet()
        val userMap = userService.getUserMapByIdIn(ownerIds)
        return tournamentEntities.map {
                tournament ->
            val type = tournamentMapper.toType(tournament)
            type.owner = userMap[tournament.createdBy]
            type
        }.toMutableList()
    }

    fun getUserTournaments(userId: String): MutableList<Tournament> {
        val userEntity = userService.getUserEntity(userId)
        val tournaments = tournamentMapper.toType(tournamentRepository.findAllByPlayers(userEntity))
        val user = userMapper.toType(userEntity)
        tournaments.forEach{tournament -> tournament.owner = user}
        return tournaments
    }

    fun getTournament(id: String): Tournament {
        val tournamentEntity = getTournamentEntity(id)
        return getTournamentWithOwner(tournamentEntity)
    }

    fun getTournamentEntity(id: String): TournamentEntity {
        return tournamentRepository.findById(id).orElseThrow { ResourceNotFoundException(
            String.format("Tournament %s not found", id)) }
    }

    fun updateTournament(id: String, input: UpdateTournamentInput): Tournament {

        val tournamentEntity = getTournamentEntity(id)

        if (tournamentEntity.name !=  input.name) {
            checkName(input.name)
        }

        val users = userService.getUserEntitiesByIdIn(input.userIds,
            null)

        tournamentEntity.name = input.name
        tournamentEntity.players = users
        tournamentRepository.save(tournamentEntity)
        return getTournamentWithOwner(tournamentEntity)
    }

    fun cancelTournament(id: String): Boolean {
        val tournamentEntity = getTournamentEntity(id)
        tournamentRepository.delete(tournamentEntity)
        return true
    }

    fun checkName(name: String?) {
        if (tournamentRepository.existsOneByName(name)) {
            throw ResourceAlreadyExistsException(
                String.format("Tournament name %s is already exists", name));
        }
    }

    fun createTournamentPlayer(id: String, userId: String): Tournament {
        val tournamentEntity = getTournamentEntity(id)
        val user = userService.getUserEntity(userId)
        if (existsTournamentPlayer(tournamentEntity, userId)) {
            throw ResourceAlreadyExistsException(
                String.format("Tournament Member %s is already exists", userId));
        }
        tournamentEntity.addPlayer(user)
        tournamentRepository.save(tournamentEntity)
        return getTournamentWithOwner(tournamentEntity)
    }

    fun deleteTournamentPlayer(id: String, userId: String): Tournament {
        val tournamentEntity = getTournamentEntity(id)
        val user = userService.getUserEntity(userId)
        if (!existsTournamentPlayer(tournamentEntity, userId)) {
            throw ResourceNotFoundException(
                String.format("Tournament Member %s is not found", userId));
        }
        tournamentEntity.removePlayer(user)
        tournamentRepository.save(tournamentEntity)
        return getTournamentWithOwner(tournamentEntity)
    }

    fun existsTournamentPlayer(tournament:TournamentEntity, userId: String): Boolean {
        return tournament.players?.any { it.id == userId } ?: false
    }

    fun getTournamentWithOwner(tournamentEntity: TournamentEntity): Tournament {
        val tournament = tournamentMapper.toType(tournamentEntity)
        tournament.owner = userService.getUser(tournamentEntity.createdBy!!)
        return tournament
    }
}