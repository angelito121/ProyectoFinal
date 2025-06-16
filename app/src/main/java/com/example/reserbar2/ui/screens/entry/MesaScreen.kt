package com.example.reserbar2.ui.screens.entry

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.reserbar2.ui.AppViewModelProvider
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.res.stringResource
import com.example.ReserBar2.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MesaScreen(
    navController: NavController,
    mesaViewModel: MesaViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var showDialog by remember { mutableStateOf(false) }
    var capacidadSeleccionada by remember { mutableStateOf(2) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.titulo_gestion_mesas), color = Color.White)
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFBB0BEE)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = Color(0xFFBB0BEE)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.añadir_mesa),
                    tint = Color.White
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            val mesas by mesaViewModel.mesas.collectAsState()

            if (mesas.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.sin_mesas))
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(mesas) { mesa ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("${mesa.nombre} - ${mesa.capacidad} ${stringResource(R.string.personas)}")
                                Button(
                                    onClick = { mesaViewModel.eliminarMesa(mesa.nombre) },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                                ) {
                                    Text(stringResource(R.string.boton_eliminar))
                                }
                            }
                        }
                    }
                }
            }
        }

        if (showDialog) {
            var nombreMesa by remember { mutableStateOf("") }

            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(stringResource(R.string.dialogo_nueva_mesa)) },
                text = {
                    Column {
                        Text(stringResource(R.string.dialogo_texto_nombre))
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = nombreMesa,
                            onValueChange = { nombreMesa = it },
                            label = { Text(stringResource(R.string.dialogo_label_nombre)) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(stringResource(R.string.dialogo_texto_capacidad))
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            listOf(2, 4, 6, 8).forEach { valor ->
                                Button(
                                    onClick = { capacidadSeleccionada = valor },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (capacidadSeleccionada == valor)
                                            MaterialTheme.colorScheme.primary
                                        else
                                            MaterialTheme.colorScheme.secondaryContainer
                                    )
                                ) {
                                    Text("$valor")
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            mesaViewModel.agregarMesa(nombreMesa, capacidadSeleccionada)
                            showDialog = false
                        },
                        enabled = nombreMesa.isNotBlank()
                    ) {
                        Text(stringResource(R.string.boton_crear))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text(stringResource(R.string.boton_cancelar))
                    }
                }
            )
        }
    }
}




