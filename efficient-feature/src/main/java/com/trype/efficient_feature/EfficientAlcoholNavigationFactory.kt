package com.trype.efficient_feature

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.trype.core.navigation.NavigationDestinations
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import net.lachlanmckee.hilt.compose.navigation.factory.ComposeNavigationFactory
import net.lachlanmckee.hilt.compose.navigation.factory.HiltComposeNavigationFactory
import javax.inject.Inject
import javax.inject.Singleton

@HiltComposeNavigationFactory
class EfficientAlcoholNavigationFactory @Inject constructor(): ComposeNavigationFactory {
    override fun create(builder: NavGraphBuilder, navController: NavHostController) {
        builder.composable(NavigationDestinations.EfficientAlcohol.route){
            EfficientAlcoholPage()
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal interface ComposeNavigationFactoryModule {
    @Singleton
    @Binds
    @IntoSet
    fun bindComposeNavigationFactory(factory: EfficientAlcoholNavigationFactory): ComposeNavigationFactory
}