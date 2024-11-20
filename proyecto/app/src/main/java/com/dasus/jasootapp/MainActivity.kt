package com.dasus.jasootapp

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.dasus.jasootapp.database.JesootDatabase
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    // Crea una instancia de la base de datos de carga tardía.
    private lateinit var database: JesootDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        // Instancia la base de datos con su contexto.
        database = JesootDatabase.getDatabase(this)

        val botonJugar = findViewById<Button>(R.id.button2)

        val animator = ObjectAnimator.ofFloat(findViewById<ImageView>(R.id.fondo), "translationY", +50f, -50f)
        animator.duration = 1000  // Duration for one oscillation (in milliseconds)
        animator.repeatCount = ObjectAnimator.INFINITE  // Repeat infinitely
        animator.repeatMode = ObjectAnimator.REVERSE  // Reverse the direction after each cycle
        animator.interpolator = OvershootInterpolator(1.5f)  // Optional: Add a bounce effect

        // Para dar comienzo a la animacion.
        animator.start()

        // Create the ObjectAnimator to rotate the ImageView 360 degrees
        val rotateAnimator = ObjectAnimator.ofFloat(findViewById<ImageView>(R.id.logo), "rotation",  10f, 0f, -10f)

        rotateAnimator.duration = 2000  // Duration of one full rotation (in milliseconds)
        rotateAnimator.repeatCount = ObjectAnimator.INFINITE  // Repeat infinitely
        rotateAnimator.repeatMode = ObjectAnimator.REVERSE  // Reverse the direction after each cycle


        rotateAnimator.start()  // Start the rotation animation



        // Este bloque de codigo "OBSERVA" una variable cuyo valor
        // depende de una llamada a la base de gatos. Al actualizarse
        // la base de datos se actualiza a su vez el codigo que depende de ella.
        database.preguntaDao().loadAllPreguntasLive().observe(this)
        {
            listado ->
            botonJugar.setOnClickListener {
                if (listado.size > 7){
                    irAJugar()
                }
                else {
                    // Muestra el error de acceso a juego.
                    muestraError()
                    muestraMensaje("¡Necesitas preguntas!")
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

                    R.id.botonInfo ->{
                        // muestra la informacion de la aplicacion.
                        ifAInfo()
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

    private fun muestraMensaje(mensaje: String) {
        findViewById<TextView>(R.id.tv_titulo2).isVisible=true
        
        Handler(Looper.getMainLooper()).postDelayed({
            findViewById<TextView>(R.id.tv_titulo2).isVisible=false
        }, 2000)
    }

    private fun ifAInfo() {
        startActivity(Intent(this, AcercaDeActivity::class.java))
    }

    private fun muestraError() {
        Toast.makeText(this,"Numero insuficiente de preguntas.", Toast.LENGTH_SHORT).show()
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