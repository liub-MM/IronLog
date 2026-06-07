package ironlog.app.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ironlog.app.data.local.dao.WorkoutDao
import ironlog.app.data.local.database.entity.WorkoutEntity
import ironlog.app.data.mappers.toDbModel
import ironlog.app.data.mappers.toDomain
import ironlog.app.data.network.parser.GeminiWorkoutParser
import ironlog.app.domain.model.Workout
import ironlog.app.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    private val geminiWorkoutParser: GeminiWorkoutParser,
    private val dao: WorkoutDao
) : WorkoutRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override suspend fun syncWorkoutToCloud(workout: Workout): Result<Unit> = runCatching {
        val userId = auth.currentUser?.uid
            ?: throw Exception("Користувач не авторизований")

        firestore.collection("users")
            .document(userId)
            .collection("workouts")
            .document(workout.id.toString())
            .set(workout)
            .await()
    }

    override fun getWorkoutById(id: Long): Flow<Workout?> {
        return dao.getWorkoutById(id).map {
            it?.toDomain()
        }
    }

    override suspend fun processAndSaveWorkout(rawText: String): Result<Workout> {
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
                val insertedWorkoutId = dao.insertFullWorkout(workout, exerciseWithSets)

                val fullWorkout = dao.getWorkoutById(insertedWorkoutId)
                    .first()?.toDomain()

                if (fullWorkout != null) {
                    Result.success(fullWorkout)
                } else {
                    Result.failure(Exception("Не вдалося отримати тренування з БД після збереження"))
                }

            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            val realError = result.exceptionOrNull() ?: RuntimeException("Some exception")
            return Result.failure(realError)
        }
    }

    override fun getWorkoutHistory(): Flow<PagingData<Workout>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            pagingSourceFactory = { dao.getPagedWorkoutsHistory() }
        ).flow.map {
            it.map { workoutWithDetails ->
                workoutWithDetails.toDomain()
            }
        }
    }

    override fun getWorkoutsForAnalytics(minTimestamp: Long): Flow<List<Workout>> {
        return dao.getWorkoutsForAnalytics(minTimestamp).map {
            it.map { workoutWithDetails ->
                workoutWithDetails.toDomain()
            }
        }
    }

    override suspend fun deleteWorkout(workoutId: Long) {
        dao.deleteWorkout(workoutId)
    }
}