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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Second view after the user clicked the registration button. It displays a thank you
 * message and the data the user entered on the view before.
 */
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

                // Format the timestamp to readable local date
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = it.success.birthdate

                val ld = LocalDate.of(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                birthdateTextField.text = ld.format(DateTimeFormatter.ofPattern(SWISS_DATE_FORMAT))
            } else if (it.error != null) {
                Toast.makeText(activity, it.error, Toast.LENGTH_LONG).show()
            }
        })

        // Attempt to load the user data
        viewModel.getUser(args.email)
    }


}