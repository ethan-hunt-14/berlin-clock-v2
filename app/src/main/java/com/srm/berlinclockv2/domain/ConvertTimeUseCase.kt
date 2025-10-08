package com.srm.berlinclockv2.domain

import data.BerlinClockState
import java.time.LocalTime

class ConvertTimeUseCase {

    fun execute(time: LocalTime): BerlinClockState {

        return BerlinClockState(
            secondsRow = getSecondsRow(time.second),
            oneMinutesRow = getOneMinutesRow(time.minute),
            fiveMinutesRow = getFiveMinutesRow(time.minute),
            oneHoursRow = getOneHoursRow(time.hour),
            fiveHoursRow = getFiveHoursRow(time.hour)
        )
    }

    private fun getSecondsRow(seconds: Int): String {
        return if (seconds % 2 == 0) "Y" else "O"
    }

    private fun getOneMinutesRow(minutes: Int): String {
        val blinkCount = minutes % 5
        return "Y".repeat(blinkCount) + "O".repeat(4 - blinkCount)
    }

    // divide it into more smaller portions
    private fun getFiveMinutesRow(minutes: Int): String {
        val blinkCount = minutes / 5
        val stringBuilder = StringBuilder()

        for (i in 1..11) {
            when {
                i <= blinkCount -> {
                    // Quarter - Red (3rd/6th/9th light)
                    if (i % 3 == 0) {
                        stringBuilder.append('R')
                    } else {
                        stringBuilder.append('Y')
                    }
                }
                else -> {
                    stringBuilder.append('O')
                }
            }
        }

        return stringBuilder.toString()
    }

    private fun getOneHoursRow(hours: Int): String {
        val blinkCount = hours % 5
        return "R".repeat(blinkCount) + "O".repeat(4 - blinkCount)
    }

    private fun getFiveHoursRow(hours: Int): String {
        val blinkCount = hours / 5
        return "R".repeat(blinkCount) + "O".repeat(4 - blinkCount)
    }

}