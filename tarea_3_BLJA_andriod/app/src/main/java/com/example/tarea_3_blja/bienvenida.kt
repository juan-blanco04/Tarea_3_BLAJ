package com.example.tarea_3_blja // <--- Verifica que este sea TU paquete real

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class bienvenida : AppCompatActivity() { // Asegúrate de que herede de AppCompatActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_bienvenida)


        val tvWelcome = findViewById<TextView>(R.id.tvWelcomeMessage)
        val btnLogout = findViewById<Button>(R.id.btnLogout)


        val userName = intent.getStringExtra("USER_NAME")


        tvWelcome.text = "¡Bienvenido, ${userName ?: "Usuario"}!"


        btnLogout.setOnClickListener {
            finish()
        }
    }
}