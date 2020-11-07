package ch.zkb.registrierung.ui.registration

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ch.zkb.registrierung.R
import ch.zkb.registrierung.databinding.RegistrationFragmentBinding
import java.util.*

class Registration3Fragment : Fragment() {

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

        registrationViewModel.registrationFormState.observe(viewLifecycleOwner, androidx.lifecycle.Observer {  })



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
    }


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