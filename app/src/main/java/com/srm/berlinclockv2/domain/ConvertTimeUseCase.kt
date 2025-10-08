package com.srm.berlinclockv2.domain

import data.BerlinClockState

class ConvertTimeUseCase {

    private val regex = "(\\d{2}):(\\d{2}):(\\d{2})".toRegex()

    fun execute(timeString: String): BerlinClockState {

        val match = regex.find(timeString)
        if (match == null || match.groupValues.size < 4) {
            return BerlinClockState()
        }

        val hours = match.groupValues[1].toInt()
        val minutes = match.groupValues[2].toInt()
        val seconds = match.groupValues[3].toInt()

        return BerlinClockState(
            secondsRow = getSecondsRow(seconds),
            oneMinutesRow = getOneMinutesRow(minutes),
            fiveMinutesRow = getFiveMinutesRow(minutes),
            oneHoursRow = getOneHoursRow(hours),
            fiveHoursRow = getFiveHoursRow(hours)
        )
    }

    private fun getSecondsRow(seconds: Int): String {
        // Seconds blink signal (Yellow - Even seconds, Off - Odd seconds)
        return if (seconds % 2 == 0) "Y" else "O"
    }

    private fun getOneMinutesRow(minutes: Int): String {
        // Each light - 1 minute - so 4 lights
        val blinkCount = minutes % 5
        return "Y".repeat(blinkCount) + "O".repeat(4 - blinkCount)
    }

    // divide it into more smaller portions
    private fun getFiveMinutesRow(minutes: Int): String {
        // Each light - 5 minutes - so 11 lights
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
        // Each light - 1 hour/remainder - so 4 lights
        val blinkCount = hours % 5
        return "R".repeat(blinkCount) + "O".repeat(4 - blinkCount)
    }

    private fun getFiveHoursRow(hours: Int): String {
        // Each light - 5 hours - so 4 lights
        val blinkCount = hours / 5
        return "R".repeat(blinkCount) + "O".repeat(4 - blinkCount)
    }

}