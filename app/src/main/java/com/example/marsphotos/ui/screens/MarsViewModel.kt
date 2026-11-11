
package com.example.marsphotos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.model.MarsPhoto
import com.example.marsphotos.network.MarsApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MarsViewModel : ViewModel() {

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
                val photos = MarsApi.retrofitService.getPhotos()

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
}
