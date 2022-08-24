package com.trype.beerstop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.trype.beerstop.ui.theme.BeerStopTheme
import dagger.hilt.android.AndroidEntryPoint
import net.lachlanmckee.hilt.compose.navigation.factory.addNavigation
import net.lachlanmckee.hilt.compose.navigation.factory.hiltNavGraphNavigationFactories
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationManager: com.trype.core.navigation.NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeerStopTheme {
                val context = LocalContext.current

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = stringResource(id = R.string.app_name))
                            }
                        )
                    }
                ) {
                    val navController = rememberNavController()
                   NavHost(navController = navController, startDestination =
                   com.trype.core.navigation.NavigationDestinations.EfficientAlcohol.route ) {
                       hiltNavGraphNavigationFactories(context).addNavigation(this, navController)
                   }

                    lifecycleScope.launchWhenStarted {
                        navigationManager
                            .navigationEvent
                            .collect{
                                navController.navigate(it.destination, it.configuration)
                            }
                    }
                }


            }
        }
    }
}