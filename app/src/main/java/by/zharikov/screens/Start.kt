package by.zharikov.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import by.zharikov.navigation.NavRoute
import by.zharikov.notesapp.ui.MainViewModel
import by.zharikov.notesapp.ui.MainViewModelFactory
import by.zharikov.notesapp.ui.theme.NotesAppTheme
import by.zharikov.utils.Constants.Keys.FIREBASE_DATABASE
import by.zharikov.utils.Constants.Keys.ROOM_DATABASE
import by.zharikov.utils.Constants.Keys.WHAT_WILL_WE_USE
import by.zharikov.utils.TYPE_FIREBASE
import by.zharikov.utils.TYPE_ROOM

@Composable
fun Start(navHostController: NavHostController, mViewModel: MainViewModel) {
    val context = LocalContext.current
    val mViewModel: MainViewModel =
        viewModel(factory = MainViewModelFactory(context.applicationContext as Application))
    Scaffold(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = WHAT_WILL_WE_USE)
            Button(
                onClick = {
                    mViewModel.initialDatabase(TYPE_ROOM) {
                        navHostController.navigate(route = NavRoute.Main.route)
                    }

                },
                modifier = Modifier
                    .width(200.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text(text = ROOM_DATABASE)
            }
            Button(
                onClick = {
                    mViewModel.initialDatabase(TYPE_FIREBASE) {
                        navHostController.navigate(route = NavRoute.Main.route)
                    }
                },
                modifier = Modifier
                    .width(200.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text(text = FIREBASE_DATABASE)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrevStartScreen() {
    NotesAppTheme() {
        val context = LocalContext.current
        val mViewModel: MainViewModel =
            viewModel(factory = MainViewModelFactory(context.applicationContext as Application))
        Start(navHostController = rememberNavController(), mViewModel = mViewModel)

    }
}