package ironlog.app.domain.usecases

import ironlog.app.domain.model.Workout
import ironlog.app.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWorkoutByIdUseCase @Inject constructor(
    private val repository: WorkoutRepository
) {
    operator fun invoke(id:Long) : Flow<Workout?>{
        return repository.getWorkoutById(id)
    }
}