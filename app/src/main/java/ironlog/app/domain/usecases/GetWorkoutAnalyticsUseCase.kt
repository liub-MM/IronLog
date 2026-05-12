package ironlog.app.domain.usecases

import ironlog.app.domain.model.Workout
import ironlog.app.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWorkoutAnalyticsUseCase @Inject constructor(val repository: WorkoutRepository) {
    operator fun invoke(minTimestamp: Long): Flow<List<Workout>> {
        return repository.getWorkoutsForAnalytics(minTimestamp)
    }
}