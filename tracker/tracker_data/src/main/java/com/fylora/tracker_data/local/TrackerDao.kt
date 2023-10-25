package com.fylora.tracker_data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.fylora.tracker_data.local.entity.TrackedFoodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackerDao {

    @Upsert
    suspend fun insertTrackedFood(trackedFoodEntity: TrackedFoodEntity)

    @Delete
    suspend fun deleteTrackedFood(trackedFoodEntity: TrackedFoodEntity)

    @Query("""
        SELECT * FROM trackedfoodentity
        WHERE dayOfMonth = :day AND month = :month AND year = :year
    """)
    fun getFoodForDate(day: Int, month: Int, year: Int): Flow<List<TrackedFoodEntity>>
}