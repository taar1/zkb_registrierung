package ch.zkb.registrierung.ui.registration

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.findNavController
import ch.zkb.registrierung.databinding.RegistrationFragmentBinding
import kotlinx.android.synthetic.main.registration_fragment.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class RegistrationFragment : Fragment() {
    private val TAG = "RegistrationFragment"
    private val SWISS_DATE_FORMAT: String = "d. MMMM yyyy"

    private val registrationViewModel: RegistrationViewModel by viewModels()

    private lateinit var viewBinding: RegistrationFragmentBinding
    var combinedCal: Calendar = GregorianCalendar(TimeZone.getTimeZone("UTC"))
    var selectedBirthdateTimestamp: Long = 0

    companion object {
        fun newInstance() = RegistrationFragment()
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

        /**
         * Observing the registrationResult object to get notified if the
         * registration was successful.
         */
        registrationViewModel.registrationResult.observe(viewLifecycleOwner, {
            if (it.error != null) {
                notificationMessage.visibility = View.VISIBLE
                notificationMessage.text = getString(it.error)
            }

            if (it.success != null) {
                notificationMessage.visibility = View.INVISIBLE

                val action =
                    RegistrationFragmentDirections.actionRegistrationFragmentToRegistrationSuccessFragment(
                        email = it.success.email
                    )
                findNavController(this).navigate(action)
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

            // Display the formatted selected birthdate
            birthdate.setText(selectedDate.format(DateTimeFormatter.ofPattern(SWISS_DATE_FORMAT)))

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
            notificationMessage.visibility = View.INVISIBLE

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
            notificationMessage.visibility = View.VISIBLE
        } else {
            container.setBackgroundColor(Color.BLACK)
            notificationMessage.visibility = View.INVISIBLE
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
            notificationMessage.visibility = View.INVISIBLE
        } else {
            datePickerLayout.visibility = View.GONE
            notificationMessage.visibility = View.VISIBLE
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
}


/**
 * Extension function to simplify setting an afterTextChanged action to EditText components
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