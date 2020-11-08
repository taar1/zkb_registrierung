package ch.zkb.registrierung.ui.registration

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
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

    private val viewModel: Registration3ViewModel by viewModels()


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
        val loading = viewBinding.loading

        setMinAndMaxDateInDatePicker()


//        registrationViewModel.registrationFormState.observe(viewLifecycleOwner, Observer {
//            val registrationState = it ?: return@Observer
//
//            // disable login button unless both username / password is valid
//            register.isEnabled = registrationState.isDataValid
//
//            if (registrationState.fullnameError != null) {
//                fullname.error = getString(registrationState.fullnameError)
//            }
//            if (registrationState.emailError != null) {
//                email.error = getString(registrationState.emailError)
//            }
//        })
//
//        registrationViewModel.registrationResult.observe(viewLifecycleOwner, Observer {
//            val loginResult = it ?: return@Observer
//
//            loading.visibility = View.GONE
//            if (loginResult.error != null) {
//                showLoginFailed(loginResult.error)
//            }
//            if (loginResult.success != null) {
//                updateUiWithUser(loginResult.success)
//            }
//
//            // TODO go to next fragment here....
//            // TODO go to next fragment here....
//            //Complete and destroy login activity once successful
//        })

        fullname.afterTextChanged {
            // TODO FIXME  Date() mit etwas richtigem ersetzen...
            // TODO FIXME  Date() mit etwas richtigem ersetzen...
            // TODO FIXME  Date() mit etwas richtigem ersetzen...
            registrationViewModel.registrationDataChanged(
                fullname.text.toString(),
                email.text.toString(),
                Date().time
            )
        }

        email.apply {
            afterTextChanged {
                // TODO FIXME  Date() mit etwas richtigem ersetzen...
                // TODO FIXME  Date() mit etwas richtigem ersetzen...
                // TODO FIXME  Date() mit etwas richtigem ersetzen...
                registrationViewModel.registrationDataChanged(
                    fullname.text.toString(),
                    email.text.toString(),
                    Date().time
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    // TODO FIXME  Date() mit etwas richtigem ersetzen...
                    // TODO FIXME  Date() mit etwas richtigem ersetzen...
                    // TODO FIXME  Date() mit etwas richtigem ersetzen...

                    EditorInfo.IME_ACTION_DONE ->
                        registrationViewModel.register(
                            fullname.text.toString(),
                            email.text.toString(),
                            Date().time
                        )
                }
                false
            }



            register.setOnClickListener {
                // TODO FIXME  Date() mit etwas richtigem ersetzen...
                // TODO FIXME  Date() mit etwas richtigem ersetzen...
                // TODO FIXME  Date() mit etwas richtigem ersetzen...
                loading.visibility = View.VISIBLE
                registrationViewModel.register(
                    fullname.text.toString(),
                    email.text.toString(),
                    Date().time
                )
            }
        }

        // User clicks into the Birthdate field to display the date picker dialog
        birthdate.setOnClickListener {
            date_picker_layout.visibility = View.VISIBLE
            container.setBackgroundColor(Color.BLACK)
        }

        // Confirmation button in Birthdate selection dialog
        confirm_date_button.setOnClickListener {
            // Hide date selector, show layout underneath
            date_picker_layout.visibility = View.GONE
            container.setBackgroundColor(Color.WHITE)

            var selectedDate = LocalDate.of(date_picker.year, date_picker.month + 1, date_picker.dayOfMonth)
            var localeFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy") // Swiss Date Format

            // Fill the EditText field with the selected date
            birthdate.setText(selectedDate.format(localeFormatter))
        }

//        register.isEnabled = true
        register.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.registrationSuccessFragment,
                null
            )
        )
    }

    private fun setMinAndMaxDateInDatePicker() {
        val minDate = Calendar.getInstance()
        minDate.set(1900, 0, 1)
        date_picker.minDate = minDate.timeInMillis

        val maxDate = Calendar.getInstance()
        maxDate.set(2019, 11, 31)
        date_picker.maxDate = maxDate.timeInMillis
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

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(activity, errorString, Toast.LENGTH_SHORT).show()
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