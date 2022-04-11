package io.github.susimsek.tournamentbackend.security

import org.springframework.data.domain.AuditorAware
import org.springframework.stereotype.Component
import java.util.*


@Component
class SpringSecurityAuditorAware : AuditorAware<String> {
    override fun getCurrentAuditor(): Optional<String> = getCurrentUserLogin()
}
