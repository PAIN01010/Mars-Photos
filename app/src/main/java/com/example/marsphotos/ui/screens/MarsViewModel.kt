
package com.example.marsphotos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.data.MarsPhotosRepository
import com.example.marsphotos.model.MarsPhoto
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface MarsUiState {
    data class Success(
        val photos: List<MarsPhoto>
    ) : MarsUiState

    data object Loading : MarsUiState

    data object Error : MarsUiState
}

class MarsViewModel(
    private val marsPhotosRepository: MarsPhotosRepository
) : ViewModel() {

    var marsUiState: MarsUiState by mutableStateOf(
        MarsUiState.Loading
    )
        private set

    init {
        getMarsPhotos()
    }

    fun getMarsPhotos() {
        viewModelScope.launch {
            marsUiState = MarsUiState.Loading

            marsUiState = try {
                val photos = marsPhotosRepository.getMarsPhotos()
                MarsUiState.Success(photos)
            } catch (e: CancellationException) {
                throw e
            } catch (e: IOException) {
                MarsUiState.Error
            } catch (e: HttpException) {
                MarsUiState.Error
            } catch (e: Exception) {
                MarsUiState.Error
            }
        }
    }

    companion object {
        fun provideFactory(
            repository: MarsPhotosRepository
        ): ViewModelProvider.Factory {

            return object : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>
                ): T {
                    return MarsViewModel(repository) as T
                }
            }
        }
    }
}
