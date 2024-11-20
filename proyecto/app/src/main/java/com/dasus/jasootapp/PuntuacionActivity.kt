package com.dasus.jasootapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PuntuacionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_puntuacion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val pregunta = findViewById<TextView>(R.id.puntuacion)

        val puntos = intent.getStringExtra("Puntuacion") ?: "0"

        pregunta.setText("Tu puntuacion es: "+puntos+"/5")

        val menu = findViewById<Button>(R.id.btn_menu)
        val jugar = findViewById<Button>(R.id.btn_otra)

        jugar.setOnClickListener{
            startActivity(Intent(this, JuegoActivity::class.java))
        }
        menu.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}