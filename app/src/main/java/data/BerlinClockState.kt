package data

data class BerlinClockState(
    val secondsRow: String = "O",
    val oneMinutesRow: String = "OOOO",
    val fiveMinutesRow: String = "OOOOOOOOOOO",
    val oneHoursRow: String = "OOOO",
    val fiveHoursRow: String = "OOOO",
)