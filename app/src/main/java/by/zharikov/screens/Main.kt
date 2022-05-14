package by.zharikov.screens

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import by.zharikov.model.Note
import by.zharikov.navigation.NavRoute
import by.zharikov.notesapp.ui.MainViewModel
import by.zharikov.notesapp.ui.MainViewModelFactory
import by.zharikov.notesapp.ui.theme.NotesAppTheme
import javax.security.auth.Subject

@Composable
fun Main(navHostController: NavHostController) {
    val context = LocalContext.current
    val mViewModel: MainViewModel =
        viewModel(factory = MainViewModelFactory(context.applicationContext as Application))
    val notes = mViewModel.readTest.observeAsState(listOf()).value
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { navHostController.navigate(NavRoute.Add.route) })
        {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add Icons",
                tint = Color.White
            )
        }

    }) {
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            NoteItem(
//                 = "Note 1",
//                subtitle = "Subtitle for 1 Note",
//                navHostController = navHostController
//            )
//            NoteItem(
//                title = "Note 2",
//                subtitle = "Subtitle for 2 Note",
//                navHostController = navHostController
//            )
//            NoteItem(
//                title = "Note 3",
//                subtitle = "Subtitle for 3 Note",
//                navHostController = navHostController
//            )
//            NoteItem(
//                title = "Note 4",
//                subtitle = "Subtitle for 4 Note",
//                navHostController = navHostController
//            )
//        }
        LazyColumn {
            items(notes) { note ->
                NoteItem(note = note, navHostController = navHostController)
            }
        }
    }
}

@Composable
fun NoteItem(note: Note, navHostController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 24.dp)
            .clickable {
                navHostController.navigate(NavRoute.Note.route)
            },
        elevation = 6.dp
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = note.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = note.subtitle)
        }

    }
}


@Preview(showBackground = true)
@Composable
fun PrevMainScreen() {
    NotesAppTheme() {
        Main(navHostController = rememberNavController())

    }
}