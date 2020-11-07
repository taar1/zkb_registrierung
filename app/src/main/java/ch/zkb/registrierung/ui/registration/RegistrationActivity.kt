package ch.zkb.registrierung.ui.registration

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import ch.zkb.registrierung.R

class RegistrationActivity : AppCompatActivity(R.layout.activity_registration) {

//    private lateinit var navController: NavController
//    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_registration)

        //setSupportActionBar(toolbar)


//        val navController = Navigation.findNavController(this, R.id.navHostFragment)


        // Navigation Component
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
//        navController = navHostFragment.navController

//        appBarConfiguration = AppBarConfiguration(navController.graph)

//        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}
