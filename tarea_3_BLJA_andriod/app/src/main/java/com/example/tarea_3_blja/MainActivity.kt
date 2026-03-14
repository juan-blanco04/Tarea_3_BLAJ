package com.example.tarea_3_blja

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


// 1. El molde para la respuesta de tu API
data class ApiResponse(
    val message: String
)

// 2. La interfaz que define las peticiones
interface ApiService {
    @GET("/")
    fun getWelcomeMessage(): Call<ApiResponse>

    // Nuevo: El endpoint para registrarse
    @POST("/register")
    fun registerUser(@Body user: UserRequest): Call<ApiResponse>

    @POST("/login")
    fun loginUser(@Body user: UserRequest): Call<ApiResponse>
}

data class UserRequest(
    val username: String,
    val password: String
)

class MainActivity : AppCompatActivity() {
    // Dentro de MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnGoToRegister = findViewById<Button>(R.id.btnGoToRegister)
        val tvResponse = findViewById<TextView>(R.id.textViewResponse)


        val okHttpClient = okhttp3.OkHttpClient.Builder()
            .connectTimeout(5, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(5, java.util.concurrent.TimeUnit.SECONDS)
            .build()


        val retrofit = retrofit2.Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/") // Cambia a tu IP real si usas el Samsung
            .client(okHttpClient)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)


        service.getWelcomeMessage().enqueue(object : retrofit2.Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: retrofit2.Response<ApiResponse>) {
                if (response.isSuccessful) {
                    tvResponse.text = "Servidor: Conectado"
                    tvResponse.setTextColor(android.graphics.Color.parseColor("#4CAF50")) // Verde
                }
            }
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                tvResponse.text = "Servidor: Desconectado"
                tvResponse.setTextColor(android.graphics.Color.parseColor("#F44336")) // Rojo
            }
        })

        // 4. Lógica del Botón de Login
        btnLogin.setOnClickListener {
            val user = etUsername.text.toString()
            val pass = etPassword.text.toString()

            if (user.isEmpty() || pass.isEmpty()) {
                tvResponse.text = "Por favor, llena todos los campos"
                return@setOnClickListener
            }

            val request = UserRequest(user, pass)

            service.loginUser(request).enqueue(object : retrofit2.Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: retrofit2.Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        // LOGIN EXITOSO: Saltamos a Bienvenida
                        val intent = Intent(this@MainActivity, bienvenida::class.java)
                        intent.putExtra("USER_NAME", user)
                        startActivity(intent)
                    } else {
                        // ERROR DE CREDENCIALES (401)
                        tvResponse.text = "Error: Usuario o contraseña incorrectos"
                        tvResponse.setTextColor(android.graphics.Color.RED)
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    // ERROR DE RED (Ejercicio 4) - Ahora tardará solo 5 seg
                    tvResponse.text = "Error de red: Servidor no disponible"
                    tvResponse.setTextColor(android.graphics.Color.RED)
                }
            })
        }


        btnGoToRegister.setOnClickListener {
            val intent = Intent(this, registro::class.java)
            startActivity(intent)
        }
    }
}