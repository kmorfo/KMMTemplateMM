package es.rlujancreations.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Raúl L.C. on 19/1/25.
 */
@Entity
data class SampleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val title: String,
)
