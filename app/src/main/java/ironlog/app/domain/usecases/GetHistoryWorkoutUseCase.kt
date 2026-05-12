package ironlog.app.domain.usecases

import androidx.paging.PagingData
import ironlog.app.domain.model.Workout
import ironlog.app.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHistoryWorkoutUseCase @Inject constructor(val repository: WorkoutRepository) {
    operator fun invoke(): Flow<PagingData<Workout>> {
        return repository.getWorkoutHistory()
    }
}