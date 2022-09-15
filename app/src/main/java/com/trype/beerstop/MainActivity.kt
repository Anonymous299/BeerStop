package com.trype.beerstop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.trype.beerstop.ui.theme.BeerStopTheme
import com.trype.core.extensions.collectWithLifecycle
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

                Scaffold{ padding ->
                    val navController = rememberNavController()
                   NavHost(navController = navController, startDestination =
                   com.trype.core.navigation.NavigationDestinations.SearchDestination.route,
                   modifier = Modifier.padding(padding)) {
                       hiltNavGraphNavigationFactories(context).addNavigation(this, navController)
                   }


                        navigationManager
                            .navigationEvent
                            .collectWithLifecycle(key = navController){
                                navController.navigate(it.destination, it.configuration)
                            }

                }


            }
        }
    }
}