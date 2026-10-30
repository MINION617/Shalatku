package com.example.shalatku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SalatKuViewModel : ViewModel() {

    //State
    private val _uiState = MutableStateFlow(SalatKuUiState())
    val uiState: StateFlow<SalatKuUiState> = _uiState.asStateFlow()

    //Tasbih
    val roundOptions: List<Int> = listOf(33, 99, 100)

    fun incrementTasbih() {
        _uiState.update { state ->
            val next = state.tasbihCount + 1
            if (next == state.roundSize) {
                state.copy(tasbihCount = 0, completedRounds = state.completedRounds + 1)
            } else {
                state.copy(tasbihCount = next)
            }
        }
    }
    fun resetTasbih() {
        _uiState.update { it.copy(tasbihCount = 0, completedRounds = 0) }
    }
    fun setRoundSize(size: Int) {
        //hitungan direset, putaran selesai tetap
        _uiState.update { it.copy(roundSize = size, tasbihCount = 0) }
    }

    //Tracker
    fun togglePrayer(name: String) {
        _uiState.update { state ->
            val done = if (name in state.donePrayers) {
                state.donePrayers - name
            } else {
                state.donePrayers + name
            }
            state.copy(donePrayers = done)
        }
    }
    fun clearPrayers() {
        _uiState.update { it.copy(donePrayers = emptyList()) }
    }

    //Home
    fun selectDay(index: Int) {
        _uiState.update { it.copy(selectedDay = index) }
    }

    //Prayer times (HW13)
    //bukan UI state, tidak ada layar yang membacanya
    private var loadCount = 0

    fun loadPrayerTimes() {
        //abaikan jika masih Loading
        if (_uiState.value.prayerTimes is PrayerTimesUiState.Loading) return

        loadCount++
        //panggilan genap gagal: 1 sukses, 2 gagal, 3 sukses, ...
        val shouldFail = loadCount % 2 == 0

        _uiState.update { it.copy(prayerTimes = PrayerTimesUiState.Loading) }

        viewModelScope.launch {
            delay(1500)
            _uiState.update {
                it.copy(
                    prayerTimes = if (shouldFail) {
                        PrayerTimesUiState.Error("Gagal memuat jadwal salat")
                    } else {
                        PrayerTimesUiState.Success(samplePrayers)
                    }
                )
            }
        }
    }
}