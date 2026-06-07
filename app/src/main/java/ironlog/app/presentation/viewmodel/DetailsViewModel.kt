package ironlog.app.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ironlog.app.domain.model.Workout
import ironlog.app.domain.usecases.GetWorkoutByIdUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCase: GetWorkoutByIdUseCase
) : ViewModel(){
    private val workoutId: Long = checkNotNull(savedStateHandle["workoutId"])

    val workoutState: StateFlow<Workout?> = useCase(workoutId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
}