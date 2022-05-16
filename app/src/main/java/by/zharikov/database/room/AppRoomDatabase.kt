package by.zharikov.database.room

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import by.zharikov.database.room.dao.NoteRoomDao
import by.zharikov.model.Note
import by.zharikov.utils.Constants
import by.zharikov.utils.Constants.Keys.NOTES_DATABASE

@Database(entities = [Note::class], version = 2)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun getRoomDao(): NoteRoomDao


    companion object {
        var MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                with(database) {
                    execSQL("CREATE TABLE notes_table_backup(id INTEGER NOT NULL DEFAULT 0, title TEXT NOT NULL, subtitle TEXT NOT NULL, firebase_id TEXT NOT NULL, PRIMARY KEY (id) )")
                    execSQL("DROP TABLE notes_table")
                    execSQL("ALTER TABLE notes_table_backup RENAME to notes_table")
                }
            }

        }

        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getInstance(context: Context): AppRoomDatabase {
            return if (INSTANCE == null) {

                INSTANCE = Room.databaseBuilder(
                    context,
                    AppRoomDatabase::class.java,
                    NOTES_DATABASE
                ).addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE as AppRoomDatabase
            } else INSTANCE as AppRoomDatabase
        }
    }
}