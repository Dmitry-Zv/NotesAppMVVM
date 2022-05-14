package by.zharikov.notesapp.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.zharikov.database.room.AppRoomDatabase
import by.zharikov.database.room.repository.RoomRepository
import by.zharikov.model.Note
import by.zharikov.utils.REPOSITORY
import by.zharikov.utils.TYPE_FIREBASE
import by.zharikov.utils.TYPE_ROOM
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
        }
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