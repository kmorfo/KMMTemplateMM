package es.rlujancreations.database

import androidx.room.RoomDatabase

/**
 * Created by Raúl L.C. on 19/1/25.
 */
expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<SampleDatabase>
}
