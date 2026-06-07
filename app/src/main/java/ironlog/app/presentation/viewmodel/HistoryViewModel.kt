package ironlog.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ironlog.app.domain.usecases.DeleteWorkoutUseCase
import ironlog.app.domain.usecases.GetHistoryWorkoutUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val deleteWorkoutUseCase: DeleteWorkoutUseCase,
    private val getHistoryWorkoutUseCase: GetHistoryWorkoutUseCase
) : ViewModel() {

    val pagedWorkouts = getHistoryWorkoutUseCase()
        .cachedIn(viewModelScope)

    fun deleteWorkout(id: Long) {
        viewModelScope.launch {
            deleteWorkoutUseCase(id)
        }
    }
}