package by.zharikov.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import by.zharikov.notesapp.ui.MainViewModel
import by.zharikov.screens.Add
import by.zharikov.screens.Main
import by.zharikov.screens.Note
import by.zharikov.screens.Start
import by.zharikov.utils.Constants
import by.zharikov.utils.Constants.Screens.ADD_SCREEN
import by.zharikov.utils.Constants.Screens.MAIN_SCREEN
import by.zharikov.utils.Constants.Screens.NOTE_SCREEN
import by.zharikov.utils.Constants.Screens.START_SCREEN


sealed class NavRoute(val route: String) {
    object Start : NavRoute(START_SCREEN)
    object Main : NavRoute(MAIN_SCREEN)
    object Add : NavRoute(ADD_SCREEN)
    object Note : NavRoute(NOTE_SCREEN)


}

@Composable
fun NotesNavHost(mViewModel: MainViewModel, navController : NavHostController) {


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
        composable(NavRoute.Note.route + "/{${Constants.Keys.ID}}") { backStackEntry ->

            Note(navHostController = navController, mViewModel = mViewModel, noteId = backStackEntry.arguments?.getString(Constants.Keys.ID))
        }

    }
}