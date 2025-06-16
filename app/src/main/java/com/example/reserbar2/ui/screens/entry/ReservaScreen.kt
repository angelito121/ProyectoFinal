import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ReserBar2.R
import com.example.reserbar2.data.model.Mesa
import com.example.reserbar2.data.model.Reserva
import com.example.reserbar2.ui.screens.entry.MesaViewModel
import com.example.reserbar2.ui.screens.entry.ReservaViewModel
import com.example.reserbar2.ui.AppViewModelProvider
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import kotlin.collections.filter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservaScreen(
    navController: NavHostController,
    mesaViewModel: MesaViewModel = viewModel(factory = AppViewModelProvider.Factory),
    reservaViewModel: ReservaViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val context = LocalContext.current
    val datePickerState = rememberDatePickerState()
    val selectedDateMillis = datePickerState.selectedDateMillis
    val today = LocalDate.now()

    val mesas by mesaViewModel.mesas.collectAsState()
    val reservas by reservaViewModel.reservas.collectAsState()

    var numPersonas by remember { mutableStateOf("") }
    var nombreCliente by remember { mutableStateOf("") }
    var mesaSeleccionada by remember { mutableStateOf<Mesa?>(null) }
    var showHoraDialog by remember { mutableStateOf(false) }

    val cantidad = numPersonas.toIntOrNull() ?: 0

    val horasDisponibles = listOf(
        "13:00", "13:30", "14:00", "14:30", "15:00", "15:30",
        "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reservar Mesa", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFBB0BEE))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top
        ) {
            Text(text = "Introduce tu nombre:")
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = nombreCliente,
                onValueChange = { nombreCliente = it },
                label = { Text("Nombre del cliente") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (nombreCliente.isBlank()) {
                Text(
                    text = "Por favor, introduce tu nombre antes de continuar.",
                    color = Color.Red
                )
            } else {
                Text("Selecciona la fecha de tu reserva:")
                DatePicker(
                    state = datePickerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                selectedDateMillis?.let {
                    val selectedDate = Instant.ofEpochMilli(it)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()

                    if (selectedDate <= today) {
                        Text("No hay reservas disponibles para esa fecha.", color = Color.Red)
                    } else {
                        Text("Has seleccionado: $selectedDate")

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = numPersonas,
                            onValueChange = { numPersonas = it },
                            label = { Text("¿Para cuántas personas?") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        if (cantidad > 0) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Mesas disponibles:", style = MaterialTheme.typography.titleMedium)

                            val mesasFiltradas = mesas.filter {
                                (it.capacidad.toIntOrNull() ?: 0) >= cantidad
                            }

                            if (mesasFiltradas.isEmpty()) {
                                Text("No hay mesas con esa capacidad.")
                            } else {
                                mesasFiltradas.forEach { mesa ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F1F1))
                                    ) {
                                        Column(modifier = Modifier.padding(16.dp)) {
                                            Text("Mesa: ${mesa.nombre}")
                                            Text("Capacidad: ${mesa.capacidad}")
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Button(
                                                onClick = {
                                                    mesaSeleccionada = mesa
                                                    showHoraDialog = true
                                                }
                                            ) {
                                                Text("Reservar esta mesa")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } ?: Text("No has seleccionado ninguna fecha aún.")
            }
        }

        // Diálogo para elegir hora
        if (showHoraDialog && mesaSeleccionada != null && selectedDateMillis != null) {
            val fecha = Instant.ofEpochMilli(selectedDateMillis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()

            val reservasMesa = reservas.filter {
                it.mesa == mesaSeleccionada!!.id && it.fecha == fecha.toString()
            }.map { it.hora }

            val horasFiltradas = horasDisponibles.filterNot { reservasMesa.contains(it) }

            AlertDialog(
                onDismissRequest = { showHoraDialog = false },
                title = { Text("Selecciona una hora") },
                text = {
                    Column {
                        if (horasFiltradas.isEmpty()) {
                            Text("No hay horas disponibles para esta mesa.")
                        } else {
                            horasFiltradas.forEach { hora ->
                                TextButton(onClick = {
                                    reservaViewModel.agregarReserva(
                                        Reserva(
                                            mesa = mesaSeleccionada!!.id,
                                            fecha = fecha.toString(),
                                            hora = hora,
                                            cliente = nombreCliente
                                        )
                                    )

                                    Toast.makeText(
                                        context,
                                        "Reserva confirmada para las $hora en la mesa ${mesaSeleccionada!!.nombre}",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    showHoraDialog = false
                                    navController.navigate("home")
                                }) {
                                    Text(hora)
                                }
                            }
                        }
                    }
                },
                confirmButton = {},
                dismissButton = {
                    TextButton(onClick = { showHoraDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}





