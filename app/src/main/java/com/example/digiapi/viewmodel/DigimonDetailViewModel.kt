package com.example.digiapi.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digiapi.data.model.DigimonDetail
import com.example.digiapi.data.repository.DigimonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DigimonDetailViewModel @Inject constructor(
    private val repository: DigimonRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<DigimonDetailUiState>(DigimonDetailUiState.Loading)
    val uiState: StateFlow<DigimonDetailUiState> = _uiState.asStateFlow()

    private val digimonName: String = checkNotNull(savedStateHandle["digimonName"])

    init {
        loadDigimonDetail()
    }

    fun loadDigimonDetail() {
        viewModelScope.launch {
            _uiState.value = DigimonDetailUiState.Loading
            repository.getDigimonByName(digimonName)
                .onSuccess { digimon ->
                    if (digimon != null) {
                        _uiState.value = DigimonDetailUiState.Success(digimon)
                    } else {
                        _uiState.value = DigimonDetailUiState.Error("Digimon not found")
                    }
                }
                .onFailure { error ->
                    _uiState.value = DigimonDetailUiState.Error(error.message ?: "Unknown error")
                }
        }
    }

    fun retry() {
        loadDigimonDetail()
    }

    sealed class DigimonDetailUiState {
        object Loading : DigimonDetailUiState()
        data class Success(val digimon: DigimonDetail) : DigimonDetailUiState()
        data class Error(val message: String) : DigimonDetailUiState()
    }
}