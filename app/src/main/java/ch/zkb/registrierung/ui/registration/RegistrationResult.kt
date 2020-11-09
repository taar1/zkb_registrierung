package ch.zkb.registrierung.ui.registration

import ch.zkb.registrierung.data.model.RegisteredUser

/**
 * Registration result: success (RegisteredUser object) or error message.
 */
data class RegistrationResult(
        val success: RegisteredUser? = null,
        val error: Int? = null
)