package io.github.susimsek.tournamentbackend.exception

import graphql.GraphQLException
import graphql.kickstart.spring.error.ThrowableGraphQLError
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.validation.ConstraintViolationException

@Component
class GraphqlExceptionHandler {
    @ExceptionHandler(GraphQLException::class, ConstraintViolationException::class)
    fun handle(e: Exception?): ThrowableGraphQLError {
        return ThrowableGraphQLError(e)
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handle(e: AuthenticationException?): ThrowableGraphQLError {
        return ThrowableGraphQLError(e, HttpStatus.UNAUTHORIZED.reasonPhrase)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handle(e: AccessDeniedException?): ThrowableGraphQLError {
        return ThrowableGraphQLError(e, HttpStatus.FORBIDDEN.reasonPhrase)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handle(e: ResourceNotFoundException?): ThrowableGraphQLError {
        return ThrowableGraphQLError(e)
    }

    @ExceptionHandler(ResourceAlreadyExistsException::class)
    fun handle(e: ResourceAlreadyExistsException?): ThrowableGraphQLError {
        return ThrowableGraphQLError(e)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handle(e: RuntimeException?): ThrowableGraphQLError {
        return ThrowableGraphQLError(e, HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase)
    }
}