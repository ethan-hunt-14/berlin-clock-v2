package com.srm.berlinclockv2.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.srm.berlinclockv2.ui.theme.BerlinClockV2Theme

class BerlinClockActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            BerlinClockV2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BerlinClockV2Screen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun BerlinClockV2Screen(
    modifier: Modifier,
    viewModel: BerlinClockViewModel = viewModel()
) {
    val state by viewModel.clockState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(Color.LightGray)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Seconds Circle portion
                Light(colorCode = state.secondsRow[0], modifier = Modifier.size(40.dp))

                Spacer(Modifier.height(16.dp))

                // 5 Hours (4 Rectangles)
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    state.fiveHoursRow.forEach { char ->
                        Light(colorCode = char, modifier = Modifier.weight(1f), isSquare = true)
                    }
                }

                Spacer(Modifier.height(8.dp))

                // 1 Hour (4 Rectangles)
                Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                    state.oneHoursRow.forEach { char ->
                        Light(colorCode = char, modifier = Modifier.weight(1f), isSquare = true)
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Minutes - 11 small lights portion
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth().height(20.dp)
                ) {
                    state.fiveMinutesRow.forEachIndexed { index, char ->
                        Light(
                            colorCode = char,
                            modifier = Modifier.weight(1f),
                            isSquare = true
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                // 1 Minute - 4 Rectangles portion
                Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                    state.oneMinutesRow.forEach { char ->
                        Light(colorCode = char, modifier = Modifier.weight(1f), isSquare = true)
                    }
                }
            }
        }
    }
}