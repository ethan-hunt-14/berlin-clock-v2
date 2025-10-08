package com.srm.berlinclockv2

import com.srm.berlinclockv2.domain.ConvertTimeUseCase
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class ConvertTimeUseCaseTest {

    private lateinit var useCase: ConvertTimeUseCase

    @Before
    fun setup() {
        useCase = ConvertTimeUseCase()
    }

    @Test
    fun testSecondsRow_shouldBeYellowForEvenSeconds() {
        val evenTime = "10:00:00"
        val state = useCase.execute(evenTime)
        assertEquals("Y", state.secondsRow)
    }

    @Test
    fun testSecondsRow_shouldBeOffForOddSeconds() {
        val oddTime = "09:00:01"
        val state = useCase.execute(oddTime)
        assertEquals("O", state.secondsRow)
    }

    @Test
    fun testOneMinuteRow_shouldHandleZeroToFourMinutes() {
        // 0 lights blink
        val zeroMinuteState = useCase.execute("10:00:00")
        assertEquals("OOOO", zeroMinuteState.oneMinutesRow)

        // 1 light blink
        val oneMinuteState = useCase.execute("10:01:00")
        assertEquals("YOOO", oneMinuteState.oneMinutesRow)

        // 4 lights blink
        val fourMinutesState = useCase.execute("10:04:00")
        assertEquals("YYYY", fourMinutesState.oneMinutesRow)

        // 5 minutes - Remainder 0, so 0 lights blink
        val fiveMinutesState = useCase.execute("10:05:00")
        assertEquals("OOOO", fiveMinutesState.oneMinutesRow)
    }

    @Test
    fun testFiveMinuteRow_shouldHandleQuartersAndIntermediateTimes() {
        // 0 minutes
        val zeroMinuteState = useCase.execute("10:00:00")
        assertEquals("OOOOOOOOOOO", zeroMinuteState.fiveMinutesRow)

        // 15 minutes
        val fifteenMinutesState = useCase.execute("10:15:00")
        assertEquals("YYROOOOOOOO", fifteenMinutesState.fiveMinutesRow)

        // 30 minutes
        val thirtyMinutesState = useCase.execute("10:30:00")
        assertEquals("YYRYYROOOOO", thirtyMinutesState.fiveMinutesRow)

        // 50 minutes
        val fiftyMinutesState = useCase.execute("10:50:00")
        assertEquals("YYRYYRYYRYO", fiftyMinutesState.fiveMinutesRow)

        // 59 minutes
        val fiftyNineMinutesState = useCase.execute("10:59:00")
        assertEquals("YYRYYRYYRYY", fiftyNineMinutesState.fiveMinutesRow)
    }

    @Test
    fun testOneHourRow_shouldHandleZeroToFourHours() {
        // 0 remainder - 0 lights blink
        val zeroHourState = useCase.execute("10:00:00")
        assertEquals("OOOO", zeroHourState.oneHoursRow)

        // 3 remainder - 3 lights blink
        val threeHourState = useCase.execute("03:00:00")
        assertEquals("RRRO", threeHourState.oneHoursRow)

        // 4 remainder - 4 lights blink
        val fourHourState = useCase.execute("04:00:00")
        assertEquals("RRRR", fourHourState.oneHoursRow)

        // 5 remainder
        val fiveHourState = useCase.execute("05:00:00")
        assertEquals("OOOO", fiveHourState.oneHoursRow)
    }

    @Test
    fun testFiveHourRow_shouldHandleFiveHourBlocks() {
        // 0 hours
        val zeroHourState = useCase.execute("00:00:00")
        assertEquals("OOOO", zeroHourState.fiveHoursRow)

        // 10 hours
        val tenHourState = useCase.execute("10:00:00")
        assertEquals("RROO", tenHourState.fiveHoursRow)

        // 23 hours
        val twentyThreeHourState = useCase.execute("23:00:00")
        assertEquals("RRRR", twentyThreeHourState.fiveHoursRow)
    }
}