package com.example.shalatku

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasbihScreen(
    count: Int,
    rounds: Int,
    roundSize: Int,
    roundOptions: List<Int>,
    onIncrement: () -> Unit,
    onReset: () -> Unit,
    onRoundSizeChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            roundOptions.forEach { size ->
                FilterChip(
                    selected = size == roundSize,
                    onClick = { onRoundSizeChange(size) },
                    label = { Text("$size") }
                )
            }
        }

        Text("$count / $roundSize", style = MaterialTheme.typography.displayMedium)
        Text("Putaran selesai: $rounds")

        Button(onClick = onIncrement) { Text("Tap") }
        OutlinedButton(onClick = onReset) { Text("Reset") }
    }
}

@Preview(showBackground = true)
@Composable
fun TasbihScreenPreview() {
    TasbihScreen(
        count = 12, rounds = 2, roundSize = 33,
        roundOptions = listOf(33, 99, 100),
        onIncrement = {}, onReset = {}, onRoundSizeChange = {}
    )
}