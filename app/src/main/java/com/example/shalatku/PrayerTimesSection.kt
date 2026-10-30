package com.example.shalatku

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PrayerTimesSection(
    state: PrayerTimesUiState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Prayer times", style = MaterialTheme.typography.titleLarge)

        when (state) {
            PrayerTimesUiState.Loading -> CircularProgressIndicator()

            is PrayerTimesUiState.Success -> {
                state.times.forEach { prayer ->
                    PrayerCard(prayer)
                }
                Button(onClick = onRefresh) { Text("Refresh") }
            }

            is PrayerTimesUiState.Error -> {
                Text(state.message, color = MaterialTheme.colorScheme.error)
                Button(onClick = onRefresh) { Text("Retry") }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PrayerTimesLoadingPreview() {
    PrayerTimesSection(PrayerTimesUiState.Loading, onRefresh = {})
}

@Preview(showBackground = true)
@Composable
fun PrayerTimesSuccessPreview() {
    PrayerTimesSection(PrayerTimesUiState.Success(samplePrayers), onRefresh = {})
}

@Preview(showBackground = true)
@Composable
fun PrayerTimesErrorPreview() {
    PrayerTimesSection(PrayerTimesUiState.Error("Gagal memuat jadwal salat"), onRefresh = {})
}