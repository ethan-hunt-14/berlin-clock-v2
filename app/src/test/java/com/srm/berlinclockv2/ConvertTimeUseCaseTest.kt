package com.srm.berlinclockv2

import com.srm.berlinclockv2.domain.ConvertTimeUseCase
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalTime

class ConvertTimeUseCaseTest {

    private lateinit var useCase: ConvertTimeUseCase

    @Before
    fun setup() {
        useCase = ConvertTimeUseCase()
    }

    @Test
    fun testSecondsRow_shouldBeYellowForEvenSeconds() {
        val evenTime = LocalTime.of(10,0,0)

        val state = useCase.execute(evenTime)

        assertEquals("Y", state.secondsRow)
    }

    @Test
    fun testSecondsRow_shouldBeOffForOddSeconds() {
        val oddTime = LocalTime.of(9,0,1)

        val state = useCase.execute(oddTime)

        assertEquals("O", state.secondsRow)
    }

    @Test
    fun testOneMinuteRow_shouldHandleZeroMinute() {
        val zeroMinuteState = useCase.execute(LocalTime.of(10, 0, 0))

        assertEquals("OOOO", zeroMinuteState.oneMinutesRow)
    }

    @Test
    fun testOneMinuteRow_shouldHandleOneMinute() {
        // 1 light blink
        val oneMinuteState = useCase.execute(LocalTime.of(10, 1, 0))

        assertEquals("YOOO", oneMinuteState.oneMinutesRow)
    }

    @Test
    fun testOneMinuteRow_shouldHandleFourMinute() {
        val fourMinutesState = useCase.execute(LocalTime.of(10, 4,0))

        assertEquals("YYYY", fourMinutesState.oneMinutesRow)
    }

    @Test
    fun testOneMinuteRow_shouldHandleFiveMinute() {
        val fourMinutesState = useCase.execute(LocalTime.of(10, 5,0))

        assertEquals("OOOO", fourMinutesState.oneMinutesRow)
    }

    @Test
    fun testFiveMinuteRow_shouldHandleQuarterZeroMinute() {
        // 0 minutes
        val zeroMinuteState = useCase.execute(LocalTime.of(10,0,0))

        assertEquals("OOOOOOOOOOO", zeroMinuteState.fiveMinutesRow)
    }

    @Test
    fun testFiveMinuteRow_shouldHandleQuarterFifteenMinute() {
        val fifteenMinutesState = useCase.execute(LocalTime.of(10,15,0))

        assertEquals("YYROOOOOOOO", fifteenMinutesState.fiveMinutesRow)
    }

    @Test
    fun testFiveMinuteRow_shouldHandleQuarterThirtyMinute() {
        val thirtyMinutesState = useCase.execute(LocalTime.of(10,30,0))

        assertEquals("YYRYYROOOOO", thirtyMinutesState.fiveMinutesRow)
    }

    @Test
    fun testFiveMinuteRow_shouldHandleQuarterFiftyMinute() {
        val fiftyMinutesState = useCase.execute(LocalTime.of(10,50,0))

        assertEquals("YYRYYRYYRYO", fiftyMinutesState.fiveMinutesRow)
    }

    @Test
    fun testFiveMinuteRow_shouldHandleQuarterFiftyNineMinute() {
        val fiftyNineMinutesState = useCase.execute(LocalTime.of(10,59,0))

        assertEquals("YYRYYRYYRYY", fiftyNineMinutesState.fiveMinutesRow)
    }

    @Test
    fun testOneHourRow_shouldHandleZeroHour() {
        // 0 remainder - 0 lights blink
        val zeroHourState = useCase.execute(LocalTime.of(10,0,0))

        assertEquals("OOOO", zeroHourState.oneHoursRow)
    }

    @Test
    fun testOneHourRow_shouldHandleThreeHour() {
        val threeHourState = useCase.execute(LocalTime.of(3,0,0))

        assertEquals("RRRO", threeHourState.oneHoursRow)
    }

    @Test
    fun testOneHourRow_shouldHandleFourHour() {
        val fourHourState = useCase.execute(LocalTime.of(4,0,0))

        assertEquals("RRRR", fourHourState.oneHoursRow)
    }

    @Test
    fun testOneHourRow_shouldHandleFiveHour() {
        val fiveHourState = useCase.execute(LocalTime.of(5,0,0))

        assertEquals("OOOO", fiveHourState.oneHoursRow)
    }

}