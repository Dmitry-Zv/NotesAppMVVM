package by.zharikov.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import by.zharikov.utils.*
import by.zharikov.utils.Constants.Keys.FIREBASE_DATABASE
import by.zharikov.utils.Constants.Keys.ROOM_DATABASE
import by.zharikov.utils.Constants.Keys.WHAT_WILL_WE_USE
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Start(navHostController: NavHostController, mViewModel: MainViewModel) {
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    var logIn by remember { mutableStateOf(Constants.Keys.EMPTY) }
    var password by remember { mutableStateOf(Constants.Keys.EMPTY) }

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
                        text = Constants.Keys.LOG_IN,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    OutlinedTextField(
                        value = logIn,
                        onValueChange = { logIn = it },
                        label = { Text(text = Constants.Keys.EMAIL) },
                        isError = logIn.isEmpty()
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(text = Constants.Keys.PASSWORD) },
                        isError = password.isEmpty()
                    )
                    Button(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = {
                            LOGIN = logIn
                            PASSWORD = password
                            mViewModel.initialDatabase(TYPE_FIREBASE) {
                                DB_TYPE = TYPE_FIREBASE
                                navHostController.navigate(NavRoute.Main.route)
                            }
                        },
                        enabled = logIn.isNotEmpty() && password.isNotEmpty()
                    ) {
                        Text(text = Constants.Keys.SIGN_IN)

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
                Text(text = WHAT_WILL_WE_USE)
                Button(
                    onClick = {
                        mViewModel.initialDatabase(TYPE_ROOM) {
                            DB_TYPE = TYPE_ROOM
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
                        coroutineScope.launch {
                            bottomSheetState.show()
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