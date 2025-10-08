package com.srm.berlinclockv2.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srm.berlinclockv2.TIME_FORMATTER
import com.srm.berlinclockv2.domain.ConvertTimeUseCase
import com.srm.berlinclockv2.getFormattedCurrentTime
import data.BerlinClockState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime

class BerlinClockViewModel(
    private val convertTimeUseCase: ConvertTimeUseCase = ConvertTimeUseCase()
) : ViewModel() {

    private val _currentTimeString = MutableStateFlow(getFormattedCurrentTime()?.format(TIME_FORMATTER))

    private val _clockState = MutableStateFlow(BerlinClockState())
    val clockState: StateFlow<BerlinClockState> = _clockState

    init {
        viewModelScope.launch {
            while (true) {
                val time = getFormattedCurrentTime()
                time?.let { it ->
                    _currentTimeString.update { it }
                    updateClock(it)
                    delay(1000L)
                }
            }
        }
    }

    private fun updateClock(time: LocalTime) {
        val newClockState = convertTimeUseCase.execute(time)
        _clockState.update { newClockState }
    }
}