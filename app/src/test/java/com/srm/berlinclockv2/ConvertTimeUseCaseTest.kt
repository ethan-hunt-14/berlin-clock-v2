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
}