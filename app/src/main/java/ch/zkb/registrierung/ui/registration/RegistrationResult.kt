package ch.zkb.registrierung.ui.registration

/**
 * Authentication result : success (user details) or error message.
 */
data class RegistrationResult(
        val success: RegisteredUserView? = null,
        val error: Int? = null
)