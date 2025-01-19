package es.rlujancreations.database

import androidx.room.RoomDatabaseConstructor

/**
 * Created by Raúl L.C. on 19/1/25.
 */
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object SampleDatabaseConstructor : RoomDatabaseConstructor<SampleDatabase> {
    override fun initialize(): SampleDatabase
}
