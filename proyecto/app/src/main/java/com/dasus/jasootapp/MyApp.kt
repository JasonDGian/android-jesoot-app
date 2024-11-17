package com.dasus.jasootapp

import android.app.Application
import com.dasus.jasootapp.database.JesootDatabase

// Clase intermedia que instancia la base de datos.
class MyApp: Application() {

    // Devuelve la base de datos.
    val database: JesootDatabase by lazy {
        JesootDatabase.getDatabase(this)
    }
}