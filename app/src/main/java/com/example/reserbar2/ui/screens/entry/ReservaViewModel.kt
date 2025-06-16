// ReservaViewModel.kt
package com.example.reserbar2.ui.screens.entry


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reserbar2.data.model.Reserva
import com.example.reserbar2.data.repository.ReservasRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ReservaViewModel(private val repository: ReservasRepository) : ViewModel() {

    val reservas = repository.getAllReservas().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun agregarReserva(reserva: Reserva) {
        viewModelScope.launch {
            repository.insertReserva(reserva)
        }
    }

    fun eliminarReserva(reserva: Reserva) {
        viewModelScope.launch {
            repository.deleteReserva(reserva)
        }
    }
}