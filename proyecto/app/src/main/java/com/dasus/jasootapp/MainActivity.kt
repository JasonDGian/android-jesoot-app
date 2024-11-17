package com.dasus.jasootapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.dasus.jasootapp.database.JesootDatabase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Crea una instancia de la base de datos.
        val database = Room.databaseBuilder( applicationContext, JesootDatabase::class.java, "jesoot_database").build()
        val preguntaDao = database.preguntaDao()

        val botonPreguntas = findViewById<Button>(R.id.button)
        val botonJugar = findViewById<Button>(R.id.button2)

        // Intent pantalla formulario
        val pantallaFormulario = Intent( this, FormularioActivity::class.java )

        botonPreguntas.setOnClickListener {
            startActivity(pantallaFormulario)
        }

        botonJugar.setOnClickListener {
            startActivity(Intent( this, ListadoPreguntasActivity::class.java ))
        }

    }
}