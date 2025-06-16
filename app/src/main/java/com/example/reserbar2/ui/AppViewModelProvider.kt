package com.example.reserbar2.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.reserbar2.Application
import com.example.reserbar2.ui.screens.entry.MesaViewModel
import com.example.reserbar2.ui.screens.entry.ReservaViewModel


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            MesaViewModel(
                todoApplication().container.mesasRepository
            )
        }

        initializer {
            ReservaViewModel(
                todoApplication().container.reservasRepository
            )
        }
    }
}

fun CreationExtras.todoApplication(): Application =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as Application)