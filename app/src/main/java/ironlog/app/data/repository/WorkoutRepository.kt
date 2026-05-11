package ironlog.app.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import ironlog.app.data.local.dao.WorkoutDao
import ironlog.app.data.local.database.entity.WorkoutEntity
import ironlog.app.data.local.database.entity.WorkoutWithDetails
import ironlog.app.data.mappers.toDbModel
import ironlog.app.data.network.parser.GeminiWorkoutParser
import ironlog.app.domain.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    private val geminiWorkoutParser: GeminiWorkoutParser,
    private val dao: WorkoutDao
) : WorkoutRepository {


    override suspend fun processAndSaveWorkout(rawText: String): Result<Unit> {
        val result = geminiWorkoutParser.parseWorkoutText(rawText)

        if (result.isSuccess) {
            val aiWorkoutResponse = result.getOrNull()!!
            return try {
                val time = System.currentTimeMillis()
                val workout = WorkoutEntity(
                    dateTimestamp = time,
                    rawInputText = rawText
                )

                val exerciseWithSets = aiWorkoutResponse.exercises.map {
                    val exerciseEntity = it.toDbModel()
                    val setEntities = it.sets.mapIndexed { index, set ->
                        set.toDbModel(setNumber = index + 1)
                    }
                    Pair(exerciseEntity, setEntities)
                }

                dao.insertFullWorkout(workout, exerciseWithSets)

                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            val realError = result.exceptionOrNull() ?: RuntimeException("Some exception")
            return Result.failure(realError)
        }
    }

    override fun getWorkoutHistory(): Flow<PagingData<WorkoutWithDetails>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            pagingSourceFactory = { dao.getPagedWorkoutsHistory() }
        ).flow
    }

    override fun getWorkoutsForAnalytics(minTimestamp: Long): Flow<List<WorkoutWithDetails>> {
        return dao.getWorkoutsForAnalytics(minTimestamp)
    }

    override suspend fun deleteWorkout(workoutId: Long) {
        dao.deleteWorkout(workoutId)
    }
}