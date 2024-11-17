package com.dasus.jasootapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dasus.jasootapp.models.Pregunta
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_juego)
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
        CoroutineScope(Dispatchers.IO).launch {
            val listaPreguntas: List<Pregunta> = preguntaDao.loadAllPreguntas()

            var cont = 0
            var respuestaSeleccionada = 0
            var puntos = 0
            while (cont<5){
                var numeroPregunta=cont+1
                titulo.setText("Pregunta $numeroPregunta/5")
                pregunta.setText(listaPreguntas[cont].pregunta)
                resp1.setText(listaPreguntas[cont].respuesta1)
                resp2.setText(listaPreguntas[cont].respuesta2)
                resp3.setText(listaPreguntas[cont].respuesta3)
                resp4.setText(listaPreguntas[cont].respuesta4)

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
                    if (respuestaSeleccionada == listaPreguntas[cont].correcta) {
                        puntos++
                    }
                    cont++
                    respuestaSeleccionada = 0
                    resp1.setBackgroundColor(ContextCompat.getColor(this@JuegoActivity, R.color.purple))
                    resp2.setBackgroundColor(ContextCompat.getColor(this@JuegoActivity, R.color.purple))
                    resp3.setBackgroundColor(ContextCompat.getColor(this@JuegoActivity, R.color.purple))
                    resp4.setBackgroundColor(ContextCompat.getColor(this@JuegoActivity, R.color.purple))
                }
            }
        }
            startActivity(Intent(this, FormularioActivity::class.java))
    }
}