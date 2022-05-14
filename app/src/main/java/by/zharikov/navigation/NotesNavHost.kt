package by.zharikov.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import by.zharikov.notesapp.ui.MainViewModel
import by.zharikov.screens.Add
import by.zharikov.screens.Main
import by.zharikov.screens.Note
import by.zharikov.screens.Start

sealed class NavRoute(val route: String) {
    object Start : NavRoute("start_screen")
    object Main : NavRoute("main_screen")
    object Add : NavRoute("add_screen")
    object Note : NavRoute("note_screen")


}

@Composable
fun NotesNavHost(mViewModel:MainViewModel) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoute.Start.route) {
        composable(NavRoute.Start.route) {
            Start(navHostController = navController, mViewModel = mViewModel)
        }
        composable(NavRoute.Main.route) {
            Main(navHostController = navController, mViewModel = mViewModel)
        }
        composable(NavRoute.Add.route) {
            Add(navHostController = navController, mViewModel = mViewModel)
        }
        composable(NavRoute.Note.route) {
            Note(navHostController = navController, mViewModel = mViewModel)
        }

    }
}