package ironlog.app.domain

import androidx.paging.PagingData
import ironlog.app.data.local.database.entity.WorkoutWithDetails
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {


    suspend fun processAndSaveWorkout(rawText: String): Result<Unit>

    fun getWorkoutHistory(): Flow<PagingData<WorkoutWithDetails>>

    fun getWorkoutsForAnalytics(minTimestamp: Long): Flow<List<WorkoutWithDetails>>

    suspend fun deleteWorkout(workoutId: Long)
}