package com.example.digiapi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.digiapi.ui.screens.DigimonDetailScreen
import com.example.digiapi.ui.screens.DigimonListScreen

sealed class Screen(val route: String) {
    object List : Screen("digimon_list")
    object Detail : Screen("digimon_detail/{digimonName}") {
        fun createRoute(digimonName: String) = "digimon_detail/$digimonName"
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.List.route
    ) {
        composable(route = Screen.List.route) {
            DigimonListScreen(
                onDigimonClick = { digimonName ->
                    navController.navigate(Screen.Detail.createRoute(digimonName))
                }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("digimonName") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val digimonName = backStackEntry.arguments?.getString("digimonName") ?: ""
            DigimonDetailScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onEvolutionClick = { evolutionName ->
                    navController.navigate(Screen.Detail.createRoute(evolutionName))
                }
            )
        }
    }
}