package io.github.susimsek.tournamentbackend.service.mapper

import org.mapstruct.BeanMapping
import org.mapstruct.MappingTarget
import org.mapstruct.Named
import org.mapstruct.NullValuePropertyMappingStrategy

interface EntityMapper<I, E, T> {

    fun toEntity(input: I): E

    fun toType(entity: E): T

    fun toEntity(inputList: MutableList<I>): MutableList<E>

    fun toType(entityList: MutableList<E>): MutableList<T>
        
    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun partialUpdate(@MappingTarget entity: E, input: I)
}
