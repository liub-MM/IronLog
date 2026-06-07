package ironlog.app.domain.usecases

import ironlog.app.domain.model.Workout
import ironlog.app.domain.repository.WorkoutRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProcessAndSaveWorkoutUseCase @Inject constructor(
    val repository: WorkoutRepository,
    private val syncToCloudUseCase: SyncWorkoutToCloudUseCase
) {
    suspend operator fun invoke(rawText: String): Result<Workout> {
        val result = repository.processAndSaveWorkout(rawText)

        result.onSuccess { savedWorkout ->
            CoroutineScope(Dispatchers.IO).launch {
                syncToCloudUseCase(savedWorkout)
            }
        }

        // 3. Повертаємо результат для ViewModel
        return result
    }
}