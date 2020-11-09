package ch.zkb.registrierung.ui.registration

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        val emailArg = args.email
        Log.d(TAG, "XXXXX onViewCreated: email: " + emailArg)


        viewModel.registeredUser.observe(viewLifecycleOwner, {
            fullnameTextField.text = it.fullname
            emailTextField.text = it.email

            // Format birthdate to readable String
            val format = SimpleDateFormat(SWISS_DATE_FORMAT, Locale.GERMANY)
            format.format(Date(it.birthdate))

            birthdateTextField.text = format.format(Date(it.birthdate))
        })


        viewModel.getUser(args.email)
    }


}