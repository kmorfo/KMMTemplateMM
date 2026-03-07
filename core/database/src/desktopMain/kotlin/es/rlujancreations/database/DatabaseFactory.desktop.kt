package es.rlujancreations.database

import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<SampleDatabase> {
        val dbFile = "${System.getProperty("user.home")}/${SampleDatabase.DB_NAME}"
        return Room.databaseBuilder<SampleDatabase>(
            name = dbFile,
        )
    }
}
