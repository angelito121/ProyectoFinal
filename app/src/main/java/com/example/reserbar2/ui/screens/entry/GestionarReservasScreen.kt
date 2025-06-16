package com.example.reserbar2.ui.screens.entry

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.reserbar2.ui.AppViewModelProvider
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.res.stringResource
import com.example.ReserBar2.R


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GestionarReservasScreen(
    navController: NavHostController,
    reservaViewModel: ReservaViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val reservas by reservaViewModel.reservas.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.titulo_gestionar_reservas),
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.volver),
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFBB0BEE))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (reservas.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.sin_reservas))
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(reservas) { reserva ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF6F6F6))
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("${stringResource(R.string.etiqueta_mesa)} ${reserva.mesa}")
                                Text("${stringResource(R.string.etiqueta_fecha)} ${reserva.fecha}")
                                Text("${stringResource(R.string.etiqueta_hora)} ${reserva.hora.format(DateTimeFormatter.ofPattern("HH:mm"))}")

                                Spacer(modifier = Modifier.height(8.dp))

                                Button(
                                    onClick = { reservaViewModel.eliminarReserva(reserva) },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                                ) {
                                    Text(stringResource(R.string.boton_eliminar), color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


