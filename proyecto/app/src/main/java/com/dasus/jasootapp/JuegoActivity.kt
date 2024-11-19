package com.dasus.jasootapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dasus.jasootapp.models.Pregunta
import com.dasus.jasootapp.viewmodels.TerminadoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JuegoActivity : AppCompatActivity() {

    private val preguntaDao by lazy {
        (application as MyApp).database.preguntaDao()
    }

    companion object{
        const val TAG = "Juego"
    }

    // Declaramos la variable de tipo observado (live data)
    private lateinit var terminadoModel: TerminadoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_juego)

        // no se que es esto.
        terminadoModel = ViewModelProvider(this).get(TerminadoViewModel::class.java)

        // Observacion de cambios en variable.
        //terminadoModel.terminadoLiveData.observe(this, Observer { newValue -> th })

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val titulo = findViewById<TextView>(R.id.titulo)
        val pregunta = findViewById<TextView>(R.id.pregunta)
        val resp1 = findViewById<Button>(R.id.btn_respuesta1)
        val resp2 = findViewById<Button>(R.id.btn_respuesta2)
        val resp3 = findViewById<Button>(R.id.btn_respuesta3)
        val resp4 = findViewById<Button>(R.id.btn_respuesta4)
        val confirmar = findViewById<Button>(R.id.btn_confirmar)

        Log.d( TAG, "Antes de corutina." )
        CoroutineScope(Dispatchers.IO).launch {
            val listaPreguntas: List<Pregunta> = preguntaDao.loadAllPreguntas()
            Log.d(TAG, listaPreguntas.toString())

            var cont = 0
            var respuestaSeleccionada = 0
            var puntos = 0
            Log.d( TAG, "Antes de while dentro de corutina." )
            var numeroPregunta=cont+1
            titulo.text=("Pregunta $numeroPregunta/5")
            Log.d( TAG, "Despues de pregunta numero X" )
            pregunta.text=(listaPreguntas[cont].pregunta)
            Log.d( TAG, "Despues de asignar pregunta" )
            resp1.text=(listaPreguntas[cont].respuesta1)
            Log.d( TAG, "Despues de asignar resp1" )
            resp2.text=(listaPreguntas[cont].respuesta2)
            Log.d( TAG, "Despues de asignar resp2" )
            resp3.text=(listaPreguntas[cont].respuesta3)
            Log.d( TAG, "Despues de asignar resp3" )
            resp4.text=(listaPreguntas[cont].respuesta4)
            Log.d( TAG, "Despues de asignar resp4" )
            Log.d( TAG, "Despues de inicialiar variables de corutina." )

            resp1.setOnClickListener {
                respuestaSeleccionada = 1
                resp1.setBackgroundColor(ContextCompat.getColor(this@JuegoActivity, R.color.yellow))
                resp2.setBackgroundColor(ContextCompat.getColor(this@JuegoActivity, R.color.purple))
                resp3.setBackgroundColor(ContextCompat.getColor(this@JuegoActivity, R.color.purple))
                resp4.setBackgroundColor(ContextCompat.getColor(this@JuegoActivity, R.color.purple))
            }
            resp2.setOnClickListener {
                respuestaSeleccionada = 2
                resp1.setBackgroundColor(ContextCompat.getColor(this@JuegoActivity, R.color.purple))
                resp2.setBackgroundColor(ContextCompat.getColor(this@JuegoActivity, R.color.yellow))
                resp3.setBackgroundColor(ContextCompat.getColor(this@JuegoActivity, R.color.purple))
                resp4.setBackgroundColor(ContextCompat.getColor(this@JuegoActivity, R.color.purple))
            }
            resp3.setOnClickListener {
                respuestaSeleccionada = 3
                resp1.setBackgroundColor(ContextCompat.getColor(this@JuegoActivity, R.color.purple))
                resp2.setBackgroundColor(ContextCompat.getColor(this@JuegoActivity, R.color.purple))
                resp3.setBackgroundColor(ContextCompat.getColor(this@JuegoActivity, R.color.yellow))
                resp4.setBackgroundColor(ContextCompat.getColor(this@JuegoActivity, R.color.purple))

            }
            resp4.setOnClickListener {
                respuestaSeleccionada = 4
                resp1.setBackgroundColor(ContextCompat.getColor(this@JuegoActivity, R.color.purple))
                resp2.setBackgroundColor(ContextCompat.getColor(this@JuegoActivity, R.color.purple))
                resp3.setBackgroundColor(ContextCompat.getColor(this@JuegoActivity, R.color.purple))
                resp4.setBackgroundColor(ContextCompat.getColor(this@JuegoActivity, R.color.yellow))
            }

            confirmar.setOnClickListener {
                if(respuestaSeleccionada!=0) {
                if (respuestaSeleccionada == listaPreguntas[cont].correcta) {
                    puntos++
                }
                cont++
                var numeroPregunta = cont + 1
                titulo.text = ("Pregunta $numeroPregunta/5")
                Log.d(TAG, "Despues de pregunta numero X")
                pregunta.text = (listaPreguntas[cont].pregunta)
                Log.d(TAG, "Despues de asignar pregunta")
                resp1.text = (listaPreguntas[cont].respuesta1)
                Log.d(TAG, "Despues de asignar resp1")
                resp2.text = (listaPreguntas[cont].respuesta2)
                Log.d(TAG, "Despues de asignar resp2")
                resp3.text = (listaPreguntas[cont].respuesta3)
                Log.d(TAG, "Despues de asignar resp3")
                resp4.text = (listaPreguntas[cont].respuesta4)
                Log.d(TAG, "Despues de asignar resp4")
                Log.d(TAG, "Despues de inicialiar variables de corutina.")

                respuestaSeleccionada = 0
                resp1.setBackgroundColor(
                    ContextCompat.getColor(
                        this@JuegoActivity,
                        R.color.purple
                    )
                )
                resp2.setBackgroundColor(
                    ContextCompat.getColor(
                        this@JuegoActivity,
                        R.color.purple
                    )
                )
                resp3.setBackgroundColor(
                    ContextCompat.getColor(
                        this@JuegoActivity,
                        R.color.purple
                    )
                )
                resp4.setBackgroundColor(
                    ContextCompat.getColor(
                        this@JuegoActivity,
                        R.color.purple
                    )
                )
                if (cont == 5) {
                    Log.d(TAG, "Despues de while dentro de corutina.")
                    terminadoModel.setTerminadoValue(true)
                    irPuntos()
                }
            }
                else{
                    Toast.makeText(this@JuegoActivity, "Tienes que seleccionar una opcion", Toast.LENGTH_SHORT).show()
                }
        }
        }

        Log.d( TAG, "Despues de corutina." )
        terminadoModel.terminadoLiveData.observe( this, Observer {
            newValue -> if ( newValue ) { startActivity(Intent(this, FormularioActivity::class.java)) }
        })

        }

    private fun irPuntos() {
        startActivity(Intent(this, FormularioActivity::class.java))
    }

}
