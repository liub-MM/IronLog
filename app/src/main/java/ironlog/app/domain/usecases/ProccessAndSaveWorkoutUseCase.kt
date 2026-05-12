package ironlog.app.domain.usecases

import ironlog.app.domain.repository.WorkoutRepository
import javax.inject.Inject

class ProcessAndSaveWorkoutUseCase @Inject constructor(val repository: WorkoutRepository) {
    suspend operator fun invoke(rawText: String): Result<Unit> {
        return repository.processAndSaveWorkout(rawText)
    }
}