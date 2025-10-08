package com.srm.berlinclockv2

import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun getFormattedCurrentTime(): String {
    return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
}
