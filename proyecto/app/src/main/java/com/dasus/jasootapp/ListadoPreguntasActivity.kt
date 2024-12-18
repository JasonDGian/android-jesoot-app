package com.dasus.jasootapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.dasus.jasootapp.dao.PreguntaDao
import com.dasus.jasootapp.models.Pregunta
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text

class ListadoPreguntasActivity : AppCompatActivity() {

    // Recupera el DAO.
    private val preguntaDao by lazy {
        (application as MyApp).database.preguntaDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listado_preguntas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recupera la vista donde inyectar preguntas.
        val linearV = findViewById<LinearLayout>(R.id.lineaScrollPeguntas)

        // observa listado de preguntas en bbd y actualiza la vista.
        preguntaDao.loadAllPreguntasLive().observe(this){
           preguntas -> cargarLive( linearV, preguntas )
        }

        // Boton para retroceder.
        findViewById<Button>(R.id.bt_atras_listado).setOnClickListener { finish() }

    }

    fun cargarLive ( linear : LinearLayout, preguntas: List<Pregunta> ){

        linear.removeAllViews()
        findViewById<TextView>(R.id.contador_preguntas).text = ("Preg. almacenadas:  " + preguntas.size.toString())

        preguntas.forEach{
                pregunta ->

                // "Infla" la vista.
                val view: View = layoutInflater.inflate(R.layout.pregunta_listado, null)

                // Configura el texto para pregunta y respuesta.
                view.findViewById<TextView>(R.id.preguntaTarjeta).text = pregunta.pregunta
                view.findViewById<TextView>(R.id.respuestaTarjeta).text = pregunta.correcta.toString()

                // Configura la función para el boton (que borre la que debe)
                view.findViewById<Button>(R.id.botonBorrarPregunta).setOnClickListener {
                    // Borra en otro hilo
                    lifecycleScope.launch(Dispatchers.IO) {
                        // Borra de la base de datos.
                        preguntaDao.deletePreguntaById(pregunta.id)

                    }
                }
                // Add the inflated view to the LinearLayout
                linear.addView(view)
        }
    }
}

