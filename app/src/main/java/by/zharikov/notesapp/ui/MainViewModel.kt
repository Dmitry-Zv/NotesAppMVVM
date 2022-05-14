package by.zharikov.notesapp.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.zharikov.model.Note
import by.zharikov.utils.TYPE_FIREBASE
import by.zharikov.utils.TYPE_ROOM
import java.lang.IllegalArgumentException

private const val TAG = "checkData"

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val readTest: MutableLiveData<List<Note>> by lazy {
        MutableLiveData<List<Note>>()
    }
    val dbType: MutableLiveData<String> by lazy {
        MutableLiveData<String>(TYPE_ROOM)
    }

    init {
        readTest.value =
            when (dbType.value) {
                TYPE_ROOM -> {
                    listOf<Note>(
                        Note(title = "Note 1", subtitle = "Subtitle for 1 Note"),
                        Note(title = "Note 2", subtitle = "Subtitle for 2 Note"),
                        Note(title = "Note 3", subtitle = "Subtitle for 3 Note"),
                        Note(title = "Note 4", subtitle = "Subtitle for 4 Note"),
                    )
                }
                TYPE_FIREBASE -> listOf()
                else -> listOf()

            }
    }

    fun initialDatabase(type: String) {
        dbType.value = type
        Log.d(TAG, "MainViewModel initDatabase with type $type")
    }
}

class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application = application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }


}