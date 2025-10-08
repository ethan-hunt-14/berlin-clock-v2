package com.srm.berlinclockv2.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srm.berlinclockv2.domain.ConvertTimeUseCase
import com.srm.berlinclockv2.getFormattedCurrentTime
import data.BerlinClockState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BerlinClockViewModel(
    private val convertTimeUseCase: ConvertTimeUseCase = ConvertTimeUseCase()
) : ViewModel() {

    private val _currentTimeString = MutableStateFlow(getFormattedCurrentTime())

    private val _clockState = MutableStateFlow(BerlinClockState())
    val clockState: StateFlow<BerlinClockState> = _clockState

    init {
        viewModelScope.launch {
            while (true) {
                val time = getFormattedCurrentTime()
                _currentTimeString.update { time }
                updateClock(time)
                delay(1000L)
            }
        }
    }

    private fun updateClock(timeString: String) {
        val newClockState = convertTimeUseCase.execute(timeString)
        _clockState.update { newClockState }
    }
}