package ironlog.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ironlog.app.domain.usecases.ProcessAndSaveWorkoutUseCase
import ironlog.app.domain.usecases.SyncWorkoutToCloudUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val processAndSaveWorkoutUseCase: ProcessAndSaveWorkoutUseCase,
    private val syncWorkoutToCloudUseCase: SyncWorkoutToCloudUseCase,
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
            val result = processAndSaveWorkoutUseCase(_workoutText.value)

            result.onSuccess {
                _workoutText.value = ""
                _uiEvent.emit("Тренування збережено!")
            }.onFailure {
                _uiEvent.emit("Не вдалося: опишіть вправи детальніше️")
            }

            _isLoading.value = false
        }
    }

}