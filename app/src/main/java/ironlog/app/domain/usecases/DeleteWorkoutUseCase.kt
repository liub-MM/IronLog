package ironlog.app.domain.usecases

import ironlog.app.domain.repository.WorkoutRepository
import javax.inject.Inject

class DeleteWorkoutUseCase @Inject constructor(val repository: WorkoutRepository) {
    suspend operator fun invoke(workoutId: Long) {
        return repository.deleteWorkout(workoutId)
    }
}