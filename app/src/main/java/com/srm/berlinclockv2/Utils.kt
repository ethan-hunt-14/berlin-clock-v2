package com.srm.berlinclockv2

import java.time.LocalTime
import java.time.format.DateTimeFormatter

val TIME_FORMATTER: DateTimeFormatter? = DateTimeFormatter.ofPattern("HH:mm:ss")

fun getFormattedCurrentTime(): LocalTime? {
    return LocalTime.now()
}
