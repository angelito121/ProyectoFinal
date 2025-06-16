// MesaViewModel.kt
package com.example.reserbar2.ui.screens.entry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reserbar2.data.model.Mesa
import com.example.reserbar2.data.repository.MesasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MesaViewModel(private val mesasRepository: MesasRepository) : ViewModel() {


    private val _mesas = MutableStateFlow<List<Mesa>>(emptyList())
    val mesas = _mesas.asStateFlow()

    init {
        viewModelScope.launch {
            mesasRepository.getAllMesa().collect {
                _mesas.value = it
            }
        }
    }

    fun agregarMesa( nombre : String ,capacidad: Int) {
        viewModelScope.launch {
            mesasRepository.insertMesa(Mesa(nombre = nombre.toString(), capacidad = capacidad.toString()))
        }
    }

    fun eliminarMesa(nombre: String) {
        viewModelScope.launch {
            val mesa = _mesas.value.find { it.nombre == nombre }
            mesa?.let {
                mesasRepository.deleteMesa(it)
            }
        }
    }

}
