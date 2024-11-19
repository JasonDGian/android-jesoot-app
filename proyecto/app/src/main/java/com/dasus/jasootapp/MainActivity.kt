package com.dasus.jasootapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.dasus.jasootapp.database.JesootDatabase
import com.dasus.jasootapp.models.Pregunta
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        CoroutineScope(Dispatchers.IO).launch {
            val listaPreguntas: List<Pregunta> = preguntaDao.loadAllPreguntas()
            botonJugar.setOnClickListener {
                if (listaPreguntas.size>=8){
                    irAJugar()
                }
                else{
                    Toast.makeText(this@MainActivity, "Necesitas tener al menos 8 preguntas", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun irAJugar() {
        startActivity(Intent(this, JuegoActivity::class.java))
    }
}