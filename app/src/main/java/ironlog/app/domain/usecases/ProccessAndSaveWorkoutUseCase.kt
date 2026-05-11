package ironlog.app.domain.usecases

import ironlog.app.domain.repository.WorkoutRepository

class ProcessAndSaveWorkoutUseCase(val repository: WorkoutRepository) {
    suspend operator fun invoke(rawText: String): Result<Unit> {
        return repository.processAndSaveWorkout(rawText)
    }
}