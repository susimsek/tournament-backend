package io.github.susimsek.tournamentbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TournamentBackendApplication

fun main(args: Array<String>) {
    runApplication<TournamentBackendApplication>(*args)
}
