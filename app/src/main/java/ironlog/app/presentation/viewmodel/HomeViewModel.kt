package ironlog.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ironlog.app.domain.usecases.ProcessAndSaveWorkoutUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val processAndSaveWorkoutUseCase: ProcessAndSaveWorkoutUseCase
) : ViewModel() {

    private val _workoutText = MutableStateFlow("")
    val workoutText = _workoutText.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _uiEvent = MutableSharedFlow<String>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onTextChanged(newText: String) {
        _workoutText.value = newText
    }

    fun proccessWorkout() {
        _isLoading.value = true
        viewModelScope.launch {
            val workout = processAndSaveWorkoutUseCase
                .invoke(_workoutText.value)
            if (workout.isSuccess) {
                _workoutText.value = ""
                emitValue("Тренування збережено!")
            } else if (workout.isFailure) {
                emitValue("Невдалося зберегти тренування")
            }
        }
    }

    private suspend fun emitValue(value: String) {
        _uiEvent.emit(value)
        _isLoading.value = false

    }

}