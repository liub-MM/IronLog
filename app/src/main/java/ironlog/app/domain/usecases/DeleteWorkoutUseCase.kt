package ironlog.app.domain.usecases

import ironlog.app.domain.repository.WorkoutRepository

class DeleteWorkoutUseCase(val repository: WorkoutRepository) {
    suspend operator fun invoke(workoutId: Long) {
        return repository.deleteWorkout(workoutId)
    }
}