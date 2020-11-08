package ch.zkb.registrierung.ui.registration

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import ch.zkb.registrierung.R
import ch.zkb.registrierung.databinding.RegistrationFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.registration_fragment.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class Registration3Fragment @Inject constructor() : Fragment() {

    private val registrationViewModel: RegistrationViewModel by viewModels()
    private lateinit var viewBinding: RegistrationFragmentBinding

    var combinedCal: Calendar = GregorianCalendar(TimeZone.getTimeZone("UTC"))
    var selectedBirthdateTimestamp: Long = 0
    val dateFormatStyle: String = "d. MMMM yyyy"

    companion object {
        fun newInstance() = Registration3Fragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = RegistrationFragmentBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fullname = viewBinding.fullName
        val email = viewBinding.email
        val birthdate = viewBinding.birthdate
        val register = viewBinding.register
        val notificationMessage = viewBinding.notificationMessage

        setMinAndMaxDateInDatePicker()

        /**
         * Observing the registrationFormState
         */
        registrationViewModel.registrationFormState.observe(viewLifecycleOwner, {
            register.isEnabled = false
            when {
                it.fullnameError != null -> {
                    notificationMessage.visibility = View.VISIBLE
                    notificationMessage.text = getString(it.fullnameError)
                }
                it.emailError != null -> {
                    notificationMessage.visibility = View.VISIBLE
                    notificationMessage.text = getString(it.emailError)
                }
                it.birthdateError != null -> {
                    notificationMessage.visibility = View.VISIBLE
                    notificationMessage.text = getString(it.birthdateError)
                }
                else -> {
                    notificationMessage.visibility = View.INVISIBLE
                    register.isEnabled = true
                }
            }
        })


        // TODO FIXME check if registration was a success (DB insert was successful)
        // TODO FIXME an dif so show the next fragment with the data.
        registrationViewModel.registrationResult.observe(viewLifecycleOwner, {

//            it.error
//            it.success
//
//            loading.visibility = View.GONE
//            if (loginResult.error != null) {
//                showLoginFailed(loginResult.error)
//            }
//            if (loginResult.success != null) {
//                updateUiWithUser(loginResult.success)
//            }


            // If success, go to next fragment
            if (true) {
                // TODO go to next fragment here....
                // TODO pass email address to make DB call
                Navigation.createNavigateOnClickListener(
                    R.id.registrationSuccessFragment,
                    null
                )
            }

        })

        fullname.afterTextChanged {
            registrationViewModel.registrationDataChanged(
                fullname.text.toString(),
                email.text.toString(),
                selectedBirthdateTimestamp
            )
        }

        email.afterTextChanged {
            registrationViewModel.registrationDataChanged(
                fullname.text.toString(),
                email.text.toString(),
                selectedBirthdateTimestamp
            )
        }

        // User clicks into the Birthdate field to display the date picker dialog
        birthdate.setOnClickListener {
            toggleMainFormElements(false)
            toggleDatePickerDialog(true)
        }

        // User clicked on conform button in the birthday selector dialog
        buttonConfirmBirthdate.setOnClickListener {
            toggleDatePickerDialog(false)
            toggleMainFormElements(true)

            combinedCal.set(
                datePicker.year,
                datePicker.month + 1,
                datePicker.dayOfMonth
            )
            selectedBirthdateTimestamp = combinedCal.timeInMillis


            val selectedDate = LocalDate.of(
                datePicker.year, datePicker.month + 1, datePicker.dayOfMonth
            )
            val localeFormatter = DateTimeFormatter.ofPattern(dateFormatStyle) // Swiss Date Format

            // Display the formatted selected birthdate
            birthdate.setText(selectedDate.format(localeFormatter))

            // Here we set the selected birthdate as timestamp
            val calendar = Calendar.getInstance()
            calendar.set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
            selectedBirthdateTimestamp = calendar.timeInMillis

            registrationViewModel.registrationDataChanged(
                fullname.text.toString(),
                email.text.toString(),
                selectedBirthdateTimestamp
            )
        }

        register.setOnClickListener {
            // TODO show a temporary loading animation.
            registrationViewModel.insertUserToDb(
                fullname.text.toString(),
                email.text.toString(),
                selectedBirthdateTimestamp
            )
        }
    }

    /**
     * Enables or disables the main form (name, email, birthdate)
     */
    private fun toggleMainFormElements(isEnabled: Boolean) {
        if (isEnabled) {
            container.setBackgroundColor(Color.WHITE)
        } else {
            container.setBackgroundColor(Color.BLACK)
        }

        fullName.isEnabled = isEnabled
        email.isEnabled = isEnabled
        birthdate.isEnabled = isEnabled
        register.isEnabled = isEnabled
    }

    /**
     * Enables or disables the date picker dialog
     */
    private fun toggleDatePickerDialog(isEnabled: Boolean) {
        if (isEnabled) {
            datePickerLayout.visibility = View.VISIBLE
        } else {
            datePickerLayout.visibility = View.GONE
        }
    }

    private fun setMinAndMaxDateInDatePicker() {
        val minDate = Calendar.getInstance()
        minDate.set(1900, 0, 1)
        datePicker.minDate = minDate.timeInMillis

        val maxDate = Calendar.getInstance()
        maxDate.set(2019, 11, 31)
        datePicker.maxDate = maxDate.timeInMillis
    }

    private val TAG = "Registration3Fragment"

    private fun updateUiWithUser(model: RegisteredUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName

        // TODO : initiate successful logged in experience
        Toast.makeText(
            activity,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }
}


/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}