package ch.zkb.registrierung.ui.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import ch.zkb.registrierung.databinding.RegistrationSuccessFragmentBinding
import java.text.SimpleDateFormat
import java.util.*


class RegistrationSuccessFragment : Fragment() {
    private val TAG = "RegistrationSuccessFrag"

    val SWISS_DATE_FORMAT: String = "d. MMMM yyyy"

    companion object {
        fun newInstance() = RegistrationSuccessFragment()
    }

    val args: RegistrationSuccessFragmentArgs by navArgs()
    val viewModel: RegistrationSuccessViewModel by viewModels()

    private lateinit var viewBinding: RegistrationSuccessFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = RegistrationSuccessFragmentBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fullnameTextField = viewBinding.valueName
        val emailTextField = viewBinding.valueEmail
        val birthdateTextField = viewBinding.valueBirthdate

        // Observing the user data from the room database.
        viewModel.registrationResult.observe(viewLifecycleOwner, {
            if (it.success != null) {
                fullnameTextField.text = it.success.fullname
                emailTextField.text = it.success.email

                // Format birthdate to readable String
                val format = SimpleDateFormat(SWISS_DATE_FORMAT, Locale.GERMANY)
                format.format(Date(it.success.birthdate))

                birthdateTextField.text = format.format(Date(it.success.birthdate))
            } else if (it.error != null) {
                Toast.makeText(activity, it.error, Toast.LENGTH_LONG).show()
            }
        })

        // Attempt to load the user data
        viewModel.getUser(args.email)
    }


}