package ch.zkb.registrierung.ui.registration

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import ch.zkb.registrierung.R

class RegistrationActivity : AppCompatActivity(R.layout.activity_registration) {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    val SWISS_DATE_FORMAT: String = "d. MMMM yyyy"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Navigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupActionBarWithNavController(navController, appBarConfiguration)
    }


    override fun onSupportNavigateUp() = findNavController(R.id.navHostFragment).navigateUp()
}