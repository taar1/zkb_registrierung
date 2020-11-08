package ch.zkb.registrierung.ui.registration

/**
 * Data validation state of the registration form.
 */
data class RegistrationFormState(
    val fullnameError: Int? = null,
    val emailError: Int? = null,
    val birthdateError: Int? = null
)