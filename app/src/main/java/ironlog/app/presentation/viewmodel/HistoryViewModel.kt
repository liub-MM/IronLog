package ironlog.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ironlog.app.domain.usecases.GetHistoryWorkoutUseCase
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryWorkoutUseCase: GetHistoryWorkoutUseCase
) : ViewModel() {

    val pagedWorkouts = getHistoryWorkoutUseCase()
        .cachedIn(viewModelScope)

}