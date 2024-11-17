package com.dasus.jasootapp

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class FormularioActivity : AppCompatActivity() {
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



        // logica de botones
        botonAtras.setOnClickListener {
            finish()
        }




    }
}