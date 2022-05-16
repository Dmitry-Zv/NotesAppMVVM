package by.zharikov.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import by.zharikov.navigation.NavRoute
import by.zharikov.notesapp.ui.MainViewModel
import by.zharikov.notesapp.ui.MainViewModelFactory
import by.zharikov.notesapp.ui.theme.NotesAppTheme
import by.zharikov.utils.Constants
import by.zharikov.utils.Constants.Keys.DELETE
import by.zharikov.utils.Constants.Keys.EDIT_NOTE
import by.zharikov.utils.Constants.Keys.EMPTY
import by.zharikov.utils.Constants.Keys.NAV_BACK
import by.zharikov.utils.Constants.Keys.NONE
import by.zharikov.utils.Constants.Keys.SUBTITLE
import by.zharikov.utils.Constants.Keys.TITLE
import by.zharikov.utils.Constants.Keys.UPDATE
import by.zharikov.utils.Constants.Keys.UPDATE_NOTE
import by.zharikov.utils.DB_TYPE
import by.zharikov.utils.TYPE_FIREBASE
import by.zharikov.utils.TYPE_ROOM
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Note(navHostController: NavHostController, mViewModel: MainViewModel, noteId: String?) {
    val notes = mViewModel.readAllNote().observeAsState(listOf()).value
    val note = when (DB_TYPE) {
        TYPE_ROOM -> {
            notes.firstOrNull { it.id == noteId?.toInt() } ?: by.zharikov.model.Note()
        }
        TYPE_FIREBASE -> {
            notes.firstOrNull { it.firebaseId == noteId } ?: by.zharikov.model.Note()
        }
        else -> by.zharikov.model.Note()
    }
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    var title by remember { mutableStateOf(EMPTY) }
    var subtitle by remember { mutableStateOf(EMPTY) }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetElevation = 5.dp,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        sheetContent = {
            Surface {
                Column(
                    modifier = Modifier
                        .padding(all = 32.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = EDIT_NOTE,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text(text = TITLE) },
                        isError = title.isEmpty()
                    )
                    OutlinedTextField(
                        value = subtitle,
                        onValueChange = { subtitle = it },
                        label = { Text(text = SUBTITLE) },
                        isError = subtitle.isEmpty()
                    )
                    Button(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = {
                            mViewModel.updateNote(
                                by.zharikov.model.Note(
                                    id = note.id,
                                    title = title,
                                    subtitle = subtitle,
                                    firebaseId = note.firebaseId
                                )
                            ) {
                                navHostController.navigate(NavRoute.Main.route)
                            }
                        }) {
                        Text(text = UPDATE_NOTE)

                    }
                }
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = note.title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 32.dp)
                        )
                        Text(
                            text = note.subtitle,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 16.dp)
                        )

                    }

                }

                Row(
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(onClick = {
                        coroutineScope.launch {

                            title = note.title
                            subtitle = note.subtitle
                            bottomSheetState.show()

                        }
                    }) {
                        Text(text = UPDATE)

                    }

                    Button(onClick = {
                        mViewModel.deleteNote(note = note) {
                            navHostController.navigate(NavRoute.Main.route)
                        }
                    }) {
                        Text(text = DELETE)

                    }
                    Button(
//                    modifier = Modifier
//                        .padding(top = 16.dp)
//                        .padding(horizontal = 32.dp)
//                        .fillMaxWidth(),
                        onClick = { navHostController.navigate(NavRoute.Main.route) }) {
                        Text(text = NAV_BACK)

                    }
                }

            }
        }
    }

}

@Composable
@Preview(showBackground = true)
fun PrevNoteScreen() {
    NotesAppTheme() {
        val context = LocalContext.current
        val mViewModel: MainViewModel =
            viewModel(factory = MainViewModelFactory(context.applicationContext as Application))
        Note(
            navHostController = rememberNavController(),
            mViewModel = mViewModel,
            noteId = "1"
        )

    }

}