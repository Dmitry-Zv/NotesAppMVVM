package by.zharikov.utils

import by.zharikov.database.DatabaseRepository

const val TYPE_DATABASE = "type_database"
const val TYPE_ROOM = "type_room"
const val TYPE_FIREBASE = "type_firebase"

lateinit var REPOSITORY : DatabaseRepository
lateinit var LOGIN : String
lateinit var PASSWORD : String

object Constants {

    object Keys{
        const val NOTES_DATABASE = "notes_database"
        const val NOTES_TABLE = "notes_table"
        const val ADD_NEW_NOTE = "add new note"
        const val NOTE_TITLE = "note title"
        const val NOTE_SUBTITLE = "note subtitle"
        const val ADD_NOTE = "Add note"
        const val TITLE = "title"
        const val EMAIL = "Email"
        const val PASSWORD = "Password"
        const val SUBTITLE = "subtitle"
        const val WHAT_WILL_WE_USE = "What will we use?"
        const val  ROOM_DATABASE = "Room Database"
        const val  FIREBASE_DATABASE = "Firebase Database"
        const val  ID = "id"
        const val  NONE = "none"
        const val  UPDATE = "Update"
        const val  DELETE = "Delete"
        const val  NAV_BACK = "Nav back"
        const val  EDIT_NOTE = "Edit note"
        const val  EMPTY = ""
        const val  UPDATE_NOTE = "Update note"
        const val  SIGN_IN = "SIGN IN"
        const val  LOG_IN = "Log in"

    }

    object Screens{
        const val START_SCREEN = "start_screen"
        const val MAIN_SCREEN = "main_screen"
        const val ADD_SCREEN = "add_screen"
        const val NOTE_SCREEN = "note_screen"

    }
}