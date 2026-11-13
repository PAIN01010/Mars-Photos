
package com.example.marsphotos.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marsphotos.MarsPhotosApplication
import com.example.marsphotos.R
import com.example.marsphotos.ui.screens.HomeScreen
import com.example.marsphotos.ui.screens.MarsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarsPhotosApp() {

    val application =
        LocalContext.current.applicationContext as MarsPhotosApplication

    val repository = application.container.marsPhotosRepository

    val factory = remember(repository) {
        MarsViewModel.provideFactory(repository)
    }

    val marsViewModel: MarsViewModel = viewModel(
        factory = factory
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        }
    ) { innerPadding ->

        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            HomeScreen(
                marsUiState = marsViewModel.marsUiState,
                contentPadding = innerPadding
            )
        }
    }
}
