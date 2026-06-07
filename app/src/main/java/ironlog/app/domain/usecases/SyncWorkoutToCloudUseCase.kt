package ironlog.app.domain.usecases

import ironlog.app.domain.model.Workout
import ironlog.app.domain.repository.WorkoutRepository
import javax.inject.Inject

class SyncWorkoutToCloudUseCase @Inject constructor(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke(workout: Workout): Result<Unit> {
        return repository.syncWorkoutToCloud(workout)
    }
}