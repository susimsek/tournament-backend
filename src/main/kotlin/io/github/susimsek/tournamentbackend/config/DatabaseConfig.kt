package io.github.susimsek.tournamentbackend.config


import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.core.convert.converter.Converter
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import java.time.Clock
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*


@Configuration(proxyBeanMethods = false)
@EnableMongoRepositories("io.github.susimsek.tournamentbackend.repository")
@Import(value = [MongoAutoConfiguration::class])
@EnableMongoAuditing(
    auditorAwareRef = "springSecurityAuditorAware",
    dateTimeProviderRef = "dateTimeProvider")
class DatabaseConfig {

    @Bean
    fun mongoCustomConversions() =
        MongoCustomConversions(
            mutableListOf<Converter<*, *>>(
                ZonedDateTimeReadConverter(),
                ZonedDateTimeWriteConverter()
            )
        )

    @Bean
    fun dateTimeProvider(clock: Clock): DateTimeProvider {
        return DateTimeProvider { Optional.of(ZonedDateTime.now(clock)) }
    }

    internal class ZonedDateTimeWriteConverter :
        Converter<ZonedDateTime, Date> {
        override fun convert(source: ZonedDateTime): Date {
            return Date.from(source.toInstant())
        }
    }

    internal class ZonedDateTimeReadConverter :
        Converter<Date, ZonedDateTime> {
        override fun convert(source: Date): ZonedDateTime {
            return ZonedDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault())
        }
    }

}
