Proyecto: App Android con Backend en Docker
En este repositorio comparto mi solución para la Tarea 3. Mi objetivo no fue solo que la app funcionara, sino entender cómo se conectan todas las piezas de un sistema real: desde la base de datos hasta la interfaz que ve el usuario.

🚀 ¿Qué hay en este proyecto?


1. El Backend (La lógica detrás de escenas)
Para el servidor usé FastAPI y Python. Lo más importante aquí fue el uso de Docker.

Lo que entendí:entendí que Docker es como una caja cerrada donde ya está todo configurado, con un comando, el servidor corre exactamente como en mi laptop.

2. La Base de Datos (SQLite)
Aunque podíamos usar una lista simple, decidí meter SQLite.

Lo que aprendí: Aprendí que sin una base de datos real si el servidor se apaga los usuarios no puede hacer nada, con SQLite, cree un archivo .db que guarda a los usuarios de forma permanente, si se apaga Docker y lo prendes, la cuenta sigue ahí.

3. La App Android (La cara del proyecto)
La app está hecha en Kotlin y usa Retrofit 2.

El reto de la red: Aprendí que una app profesional no puede quedarse trabada si el internet falla, configure un timeout de 5 segundos para que la app le avise rápido al usuario si el servidor no responde, en lugar de dejarlo esperando un minuto.

🛠️ Cómo correr el proyecto
Para que todo funcione a la primera, sigue estos pasos:

1. Levantar el servidor
Entra a la carpeta /backend desde la terminal y ejecuta:

Bash
docker-compose up --build
Esto creará el contenedor y pondrá la API a escuchar en el puerto 5000.

2. Configurar la IP en Android
En el archivo MainActivity.kt y registro.kt:

Si se usa emulador: http://10.0.2.2:5000/

Si se usas dispositivo físico: Usa la IP de la computadora

3. Probar
Registro: Crea un usuario nuevo.

Login: Entra y se vera la pantalla de bienvenida.

Error de Red: Apaga el Docker e intenta loguearte; se vera el mensaje de error en rojo que configuramos.

🧠 Conclusión personal
Este proyecto me ayudó a entender que el desarrollo móvil no es solo poner botones, sino saber gestionar la comunicación con un servidor.

Cpaturas de pantalla de la tarea

Esta pantalla confirma que la app y el servidor ya se comunican, lo cual logramos creando un backend en Python y montándolo dentro de un contenedor de Docker para que la conexión sea estable y fácil de ejecutar.





















