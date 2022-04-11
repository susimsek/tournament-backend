package io.github.susimsek.tournamentbackend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.*

@Configuration
class ClockConfig {

    @Bean
    fun testClock(): Clock {
        return Clock.fixed(Instant.parse(DEFAULT_DATE), ZoneId.systemDefault())
    }

    companion object {
        const val DEFAULT_DATE = "2020-10-16T03:00:00+03:00"
    }
}