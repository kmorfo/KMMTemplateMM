package es.rlujancreations.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import es.rlujancreations.database.dao.SampleDAO
import es.rlujancreations.database.entities.SampleEntity

/**
 * Created by Raúl L.C. on 19/1/25.
 */
@Database(
    entities = [SampleEntity::class],
    version = 1,
)
@ConstructedBy(SampleDatabaseConstructor::class)
abstract class SampleDatabase : RoomDatabase() {
    abstract val sampleDAO: SampleDAO

    companion object {
        const val DB_NAME = "sample.db"
    }
}
