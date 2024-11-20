package com.dasus.jasootapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.PopupMenu
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
        val database =
            Room.databaseBuilder(applicationContext, JesootDatabase::class.java, "jesoot_database")
                .build()
        val preguntaDao = database.preguntaDao()

        val botonPreguntas = findViewById<Button>(R.id.button)
        val botonJugar = findViewById<Button>(R.id.button2)

        // Intent pantalla formulario
        val pantallaFormulario = Intent(this, FormularioActivity::class.java)

        botonPreguntas.setOnClickListener {
            startActivity(pantallaFormulario)
        }
        CoroutineScope(Dispatchers.IO).launch {
            val listaPreguntas: List<Pregunta> = preguntaDao.loadAllPreguntas()
            botonJugar.setOnClickListener {
                if (listaPreguntas.size >= 8) {
                    irAJugar()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Necesitas tener al menos 8 preguntas",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // -- Seccion menu contextual.

        // Identifica el boton para el menu contextual.
        val botonContextual = findViewById<Button>(R.id.boton_menu)

        // configura el comportamiento del boton del menu contextual.
        botonContextual.setOnClickListener {
                view -> // vista que recibe.
            // Identifica o genera el menu emergente.
            val menuEmergente = PopupMenu(this, view)
            // Busca la vista del menu contextual y lo mete en el menu emergente creado.
            menuEmergente.menuInflater.inflate(R.menu.menu_cascada, menuEmergente.menu)

            // Aplica un comportamiento a las entradas del menu inflado.
            menuEmergente.setOnMenuItemClickListener { menuItem: MenuItem ->
                when (menuItem.itemId) {

                    R.id.botonFormulario -> {
                        // Abre actividad Configuracion.
                        ifAConfiguracion()
                        true
                    }

                    R.id.botonListado -> {
                        // Abre actividad Listado Preguntas.
                        irAListado()
                        true
                    }
                    // Caso por defecto del when.
                    else -> false
                }
            }

            // Muestra el menu en pantalla una vez configurado.
            menuEmergente.show()
        }
    }

    private fun irAListado() {
        startActivity(Intent(this, ListadoPreguntasActivity::class.java))
    }

    private fun ifAConfiguracion() {
        startActivity(Intent(this, FormularioActivity::class.java))
    }

    private fun irAJugar() {
        startActivity(Intent(this, JuegoActivity::class.java))
    }
}