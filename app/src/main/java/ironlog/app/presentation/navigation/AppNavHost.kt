package ironlog.app.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ironlog.app.presentation.screen.MainScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.Home,
        modifier = modifier,
    ) {
        composable<NavRoute.Home> {
            MainScreen(
                onHomeButtonClick = { navController.navigate(NavRoute.Home) },
                onProgressButtonClick = { navController.navigate(NavRoute.Progress) },
                onHistoryButtonClick = { navController.navigate(NavRoute.History) }
            )
        }

        composable<NavRoute.Progress> {

        }

        composable<NavRoute.History> {

        }


    }
}