package by.zharikov.notesapp.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import by.zharikov.database.firebase.FireBaseRepository
import by.zharikov.database.room.AppRoomDatabase
import by.zharikov.database.room.repository.RoomRepository
import by.zharikov.model.Note
import by.zharikov.utils.REPOSITORY
import by.zharikov.utils.TYPE_FIREBASE
import by.zharikov.utils.TYPE_ROOM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

private const val TAG = "checkData"

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val context = application
    fun initialDatabase(type: String, onSuccess: () -> Unit) {
        Log.d(TAG, "MainViewModel initDatabase with type $type")
        when (type) {
            TYPE_ROOM -> {
                val dao = AppRoomDatabase.getInstance(context).getRoomDao()
                REPOSITORY = RoomRepository(dao)
                onSuccess()
            }
            TYPE_FIREBASE -> {
                REPOSITORY = FireBaseRepository()
                REPOSITORY.connectToDatabase({
                    onSuccess()
                },
                    {
                        Log.d(TAG, "Error ${it}")
                    })
            }
        }
    }

    fun addNote(note: Note, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.create(note = note) {
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    fun updateNote(note: Note, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.update(note = note) {
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    fun deleteNote(note: Note, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.delete(note) {
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    fun readAllNote() = REPOSITORY.readAll
}

class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application = application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }


}