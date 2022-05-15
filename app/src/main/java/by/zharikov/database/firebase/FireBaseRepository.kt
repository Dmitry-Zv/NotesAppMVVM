package by.zharikov.database.firebase

import androidx.lifecycle.LiveData
import by.zharikov.database.DatabaseRepository
import by.zharikov.model.Note
import by.zharikov.utils.LOGIN
import by.zharikov.utils.PASSWORD
import com.google.firebase.auth.FirebaseAuth

class FireBaseRepository : DatabaseRepository {

    private val mAuh = FirebaseAuth.getInstance()
    override val readAll: LiveData<List<Note>>
        get() = TODO("Not yet implemented")

    override suspend fun create(note: Note, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun update(note: Note, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(note: Note, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        mAuh.signOut()
    }

    override fun connectToDatabase(onSuccess: () -> Unit, onFail: (String) -> Unit) {
        mAuh.signInWithEmailAndPassword(LOGIN, PASSWORD).addOnSuccessListener {
            onSuccess()
        }
            .addOnFailureListener {
                mAuh.createUserWithEmailAndPassword(LOGIN, PASSWORD).addOnSuccessListener {
                    onSuccess()
                }
                    .addOnFailureListener {
                        onFail(it.message.toString())
                    }
            }
    }
}