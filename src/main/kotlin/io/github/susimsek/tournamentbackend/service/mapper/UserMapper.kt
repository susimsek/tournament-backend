package io.github.susimsek.tournamentbackend.service.mapper


import io.github.susimsek.tournamentbackend.domain.UserEntity
import io.github.susimsek.tournamentbackend.graphql.input.CreateUserInput
import io.github.susimsek.tournamentbackend.graphql.type.User
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserMapper : EntityMapper<CreateUserInput, UserEntity, User> {
}
