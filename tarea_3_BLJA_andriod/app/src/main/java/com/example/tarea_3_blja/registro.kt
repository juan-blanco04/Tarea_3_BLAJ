package com.example.tarea_3_blja

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro) // Asegúrate que tu XML se llame así

        val etUser = findViewById<EditText>(R.id.etRegUsername)
        val etPass = findViewById<EditText>(R.id.etRegPassword)
        val btnDoRegister = findViewById<Button>(R.id.btnDoRegister)
        val tvRegResponse = findViewById<TextView>(R.id.tvRegResponse)

        val retrofit = retrofit2.Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/")
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)

        btnDoRegister.setOnClickListener {
            val u = etUser.text.toString()
            val p = etPass.text.toString()

            if (u.isEmpty() || p.isEmpty()) {
                tvRegResponse.text = "Datos incompletos"
                return@setOnClickListener
            }

            val request = UserRequest(u, p)
            service.registerUser(request).enqueue(object : retrofit2.Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: retrofit2.Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@registro, "¡Usuario creado!", Toast.LENGTH_SHORT).show()
                        finish() // Cerramos esta pantalla y regresamos al Login
                    } else {
                        tvRegResponse.text = "El usuario ya existe"
                    }
                }
                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    tvRegResponse.text = "Error: ${t.message}"
                }
            })
        }
    }

}