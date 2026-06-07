package ironlog.app.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import ironlog.app.presentation.screen.HistoryScreen
import ironlog.app.presentation.screen.MainScreen
import ironlog.app.presentation.screen.ProgressScreen
import ironlog.app.presentation.screen.WorkoutDetailsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        bottomBar = {
            IronLogBottomNavigation(navController = navController)
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = NavRoute.Home,
            modifier = modifier.padding(innerPadding),
        ) {
            composable<NavRoute.Home> {
                MainScreen()
            }

            composable<NavRoute.Progress> {
                ProgressScreen()
            }

            composable<NavRoute.WorkoutDetails> { backStackEntry ->
                val route = backStackEntry.toRoute<NavRoute.WorkoutDetails>()

                WorkoutDetailsScreen(
                    onBackClick = { navController.popBackStack() },
                    workoutId = route.workoutId
                )
            }

            composable<NavRoute.History> {
                HistoryScreen(
                    onWorkoutClick = {
                        navController.navigate(NavRoute.WorkoutDetails(it))
                    }
                )
            }
        }
    }
}

@Composable
fun IronLogBottomNavigation(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        NavigationBarItem(
            selected = currentDestination?.hierarchy?.any { it.hasRoute<NavRoute.Home>() } == true,
            onClick = { navigateBottomBar(navController, NavRoute.Home) },
            icon = { Icon(Icons.Default.FitnessCenter, contentDescription = "Головна") },
            label = { Text("Тренування") }
        )

        NavigationBarItem(
            selected = currentDestination?.hierarchy?.any { it.hasRoute<NavRoute.History>() } == true,
            onClick = { navigateBottomBar(navController, NavRoute.History) },
            icon = { Icon(Icons.Default.History, contentDescription = "Історія") },
            label = { Text("Історія") }
        )

        NavigationBarItem(
            selected = currentDestination?.hierarchy?.any { it.hasRoute<NavRoute.Progress>() } == true,
            onClick = { navigateBottomBar(navController, NavRoute.Progress) },
            icon = { Icon(Icons.Default.Analytics, contentDescription = "Прогрес") },
            label = { Text("Прогрес") }
        )
    }
}

private fun navigateBottomBar(navController: NavHostController, route: Any) {
    navController.navigate(route) {
        popUpTo(navController.graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}