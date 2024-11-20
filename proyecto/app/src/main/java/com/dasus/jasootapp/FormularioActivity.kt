package com.dasus.jasootapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.dasus.jasootapp.models.Pregunta
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class FormularioActivity : AppCompatActivity() {

    // Recupera el DAO.
    private val preguntaDao by lazy {
        (application as MyApp).database.preguntaDao()
    }

    companion object{
       const val TAG = "Formulario"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_formulario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Asignación a variables.
        val editorPregunta = findViewById<EditText>(R.id.et_pregunta)
        val editorRespuesta1 = findViewById<EditText>(R.id.et_respuesta1)
        val editorRespuesta2 = findViewById<EditText>(R.id.et_respuesta2)
        val editorRespuesta3 = findViewById<EditText>(R.id.et_respuesta3)
        val editorRespuesta4 = findViewById<EditText>(R.id.et_respuesta4)
        val botonGuardar = findViewById<Button>(R.id.bt_guardar)
        val botonAtras = findViewById<Button>(R.id.bt_atras)

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val respCorrecta1 = findViewById<RadioButton>(R.id.rb_resp1)
        val respCorrecta2 = findViewById<RadioButton>(R.id.rb_resp2)
        val respCorrecta3 = findViewById<RadioButton>(R.id.rb_resp3)
        val respCorrecta4 = findViewById<RadioButton>(R.id.rb_resp4)

        var botonRespuestaSeleccionado = 0

        // logica de botones
        botonAtras.setOnClickListener {
            finish()
        }

        radioGroup.setOnCheckedChangeListener {
          _, checkedId ->
            if ( respCorrecta1.isChecked ){
                Toast.makeText(this, "Respuesta correcta 1", Toast.LENGTH_SHORT).show()
                botonRespuestaSeleccionado = 1
            }
            if ( respCorrecta2.isChecked ){
                Toast.makeText(this, "Respuesta correcta 2", Toast.LENGTH_SHORT).show()
                botonRespuestaSeleccionado = 2
            }
            if ( respCorrecta3.isChecked ){
                Toast.makeText(this, "Respuesta correcta 3", Toast.LENGTH_SHORT).show()
                botonRespuestaSeleccionado = 3
            }
            if ( respCorrecta4.isChecked ){
            Toast.makeText(this, "Respuesta correcta 4", Toast.LENGTH_SHORT).show()
                botonRespuestaSeleccionado = 4
            }
        }

        botonGuardar.setOnClickListener {
            Log.d("Formulario:", "Boton guardar pulsado.")

            // Control de valores nulos.
            if ( editorPregunta.text.isBlank() ||
                editorRespuesta1.text.isBlank() ||
                editorRespuesta2.text.isBlank() ||
                editorRespuesta3.text.isBlank() ||
                editorRespuesta4.text.isBlank() ||
                botonRespuestaSeleccionado==0
                ){
                Toast.makeText(this, "ERROR: Rellene todos los campos del formulario", Toast.LENGTH_SHORT).show()
            }

            // En caso de que las validaciones pasen.
            else{

                // Objeto Pregunta para su guardado.
                var pregunta = Pregunta(
                    0, // Dejando el ID a 0 lo gestionará ROOM.
                    "",
                    "",
                    "",
                    "",
                    "",
                    0
                )
                Log.d(TAG, "Objeto pregunta creado.")

                pregunta.pregunta=editorPregunta.text.toString()
                pregunta.respuesta1=editorRespuesta1.text.toString()
                pregunta.respuesta2=editorRespuesta2.text.toString()
                pregunta.respuesta3=editorRespuesta3.text.toString()
                pregunta.respuesta4=editorRespuesta4.text.toString()
                pregunta.correcta=botonRespuestaSeleccionado

                Log.d(TAG, "Objeto pregunta rellenado.")

                // Guarda la pregunta en BBDD
                lifecycleScope.launch {
                    preguntaDao.upsertPregunta(pregunta)
                }

                Log.d(TAG, "Objeto guardado en BBDD.")

                // Resetea campos de texto.
                editorPregunta.setText("")
                editorRespuesta1.setText("")
                editorRespuesta2.setText("")
                editorRespuesta3.setText("")
                editorRespuesta4.setText("")

                Toast.makeText(this, "Pregunta almacenada con éxito", Toast.LENGTH_SHORT).show()
            }



        }

    }

}