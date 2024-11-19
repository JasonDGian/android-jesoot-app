package com.dasus.jasootapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// clase de observacion de variable booleana.
class TerminadoViewModel : ViewModel() {
    private val _terminadoLiveData = MutableLiveData<Boolean>()
    val terminadoLiveData: LiveData<Boolean> get() = _terminadoLiveData

    fun setTerminadoValue(value: Boolean) {
        _terminadoLiveData.value = value
    }
}
