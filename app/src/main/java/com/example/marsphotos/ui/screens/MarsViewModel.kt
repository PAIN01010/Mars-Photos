
package com.example.marsphotos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.data.MarsPhotosRepository
import com.example.marsphotos.model.MarsPhoto
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MarsViewModel(
    private val marsPhotosRepository: MarsPhotosRepository
) : ViewModel() {

    var marsUiState: String by mutableStateOf(
        "Cargando fotos de Marte..."
    )
        private set

    var marsPhotos: List<MarsPhoto> by mutableStateOf(emptyList())
        private set

    init {
        getMarsPhotos()
    }

    fun getMarsPhotos() {
        viewModelScope.launch {

            marsUiState = "Cargando fotos de Marte..."

            try {
                val photos = marsPhotosRepository.getMarsPhotos()

                marsPhotos = photos

                marsUiState =
                    "Se encontraron ${photos.size} fotos de Marte"

            } catch (e: IOException) {
                marsUiState = "Error de conexion a Internet"

            } catch (e: HttpException) {
                marsUiState = "Error al obtener las fotos"

            } catch (e: Exception) {
                marsUiState = "No se pudieron cargar las fotos"
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
