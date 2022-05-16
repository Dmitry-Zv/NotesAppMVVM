package by.zharikov.database.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import by.zharikov.database.DatabaseRepository
import by.zharikov.model.Note
import by.zharikov.utils.Constants.Keys.SUBTITLE
import by.zharikov.utils.Constants.Keys.TITLE
import by.zharikov.utils.FIREBASE_ID
import by.zharikov.utils.LOGIN
import by.zharikov.utils.PASSWORD
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FireBaseRepository : DatabaseRepository {

    private val mAuh = FirebaseAuth.getInstance()
    private val database = Firebase.database.reference.child(mAuh.currentUser?.uid.toString())
    override val readAll: LiveData<List<Note>> = AllNotesLiveData()


    override suspend fun create(note: Note, onSuccess: () -> Unit) {
       val noteId = database.push().key.toString()
        val mapNotes = hashMapOf<String, Any>()
        mapNotes[FIREBASE_ID] = noteId
        mapNotes[TITLE] = note.title
        mapNotes[SUBTITLE] = note.subtitle

        database.child(noteId)
            .updateChildren(mapNotes)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener{
                Log.d("check data", "Failed to add new note!")
            }
    }

    override suspend fun update(note: Note, onSuccess: () -> Unit) {
        val noteId = note.firebaseId
        val mapNotes = hashMapOf<String, Any>()
        mapNotes[FIREBASE_ID] = noteId
        mapNotes[TITLE] = note.title
        mapNotes[SUBTITLE] = note.subtitle

        database.child(noteId)
            .updateChildren(mapNotes)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                Log.d("check data", "Failed to update note!")
            }
    }

    override suspend fun delete(note: Note, onSuccess: () -> Unit) {
       database.child(note.firebaseId)
           .removeValue()
           .addOnSuccessListener {
               onSuccess()
           }
           .addOnFailureListener {
               Log.d("check data", "Failed to delete note!")
           }
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