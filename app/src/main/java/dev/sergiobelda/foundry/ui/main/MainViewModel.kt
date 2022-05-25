package dev.sergiobelda.foundry.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.sergiobelda.foundry.domain.result.doIfError
import dev.sergiobelda.foundry.domain.result.doIfSuccess
import dev.sergiobelda.foundry.domain.usecase.FetchFontsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(
    private val fetchFontsUseCase: FetchFontsUseCase
) : ViewModel() {

    var mainUiState: MainUiState by mutableStateOf(MainUiState(isLoadingFonts = true))
        private set

    init {
        fetchFonts()
    }

    private fun fetchFonts() = viewModelScope.launch {
        delay(2000)
        fetchFontsUseCase().doIfSuccess { fonts ->
            mainUiState = mainUiState.copy(
                isLoadingFonts = false,
                fonts = fonts
            )
        }.doIfError {
            mainUiState = mainUiState.copy(
                isLoadingFonts = false,
                fonts = emptyList()
            )
        }
    }
}
