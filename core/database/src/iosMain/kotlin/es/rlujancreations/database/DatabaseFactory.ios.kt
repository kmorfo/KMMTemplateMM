package es.rlujancreations.database

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

/**
 * Created by Raúl L.C. on 19/1/25.
 */
actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<SampleDatabase> {
        val dbFile = documentDirectory() + "/${SampleDatabase.DB_NAME}"
        return Room.databaseBuilder<SampleDatabase>(
            name = dbFile,
        )
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun documentDirectory(): String {
        val documentDirectory =
            NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null,
            )
        return requireNotNull(documentDirectory?.path)
    }
}
