package es.rlujancreations.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import es.rlujancreations.database.entities.SampleEntity

/**
 * Created by Raúl L.C. on 19/1/25.
 */
@Dao
interface SampleDAO {
    @Query("SELECT * FROM SampleEntity")
    suspend fun getAll(): List<SampleEntity>

    @Query("SELECT * FROM SampleEntity WHERE id = :id")
    suspend fun getById(id: Int): SampleEntity

    @Upsert
    suspend fun upsert(entity: SampleEntity)

    @Delete
    suspend fun delete(entity: SampleEntity)

    @Query("DELETE FROM SampleEntity WHERE id = :id")
    suspend fun deleteById(id: Int)
}
