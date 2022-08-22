package com.trype.beerstop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.trype.beerstop.ui.theme.BeerStopTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import net.lachlanmckee.hilt.compose.navigation.factory.addNavigation
import net.lachlanmckee.hilt.compose.navigation.factory.hiltNavGraphNavigationFactories
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

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
                   NavigationDestinations.EfficientAlcohol.route ) {
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