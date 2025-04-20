package es.rlujancreations.database

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

/**
 * Created by Raúl L.C. on 19/1/25.
 */
actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<SampleDatabase> {
        val os = System.getProperty("os.name").lowercase()
        val userHome = System.getProperty("user.home")
        val appDataDir =
            when {
                os.contains("win") -> File(System.getenv("APPDATA"), "kmmtemplatemm")
                os.contains("mac") -> File(userHome, "Library/Application Support/kmmtemplatemm")
                else -> File(userHome, ".local/share/kmmtemplatemm")
            }

        if (!appDataDir.exists()) {
            appDataDir.mkdirs()
        }

        val dbFile = File(appDataDir, SampleDatabase.DB_NAME)
        return Room.databaseBuilder(dbFile.absolutePath)
    }
}
