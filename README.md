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

Capturas de pantalla de la tarea

Esta pantalla confirma que la app y el servidor ya se comunican, lo cual logramos creando un backend en Python y montándolo dentro de un contenedor de Docker para que la conexión sea estable y fácil de ejecutar.

<img width="588" height="1265" alt="Image" src="https://github.com/user-attachments/assets/cdc1e53a-1832-4634-8df5-155cc996f986" />

Pantalla de Inicio de Sesión: En esta parte la app verifica que el usuario y la contraseña sean correctos consultando la base de datos, confirmando con el mensaje en verde que nuestro servidor en Docker está encendido y listo para dejarnos pasar.

<img width="624" height="1354" alt="Image" src="https://github.com/user-attachments/assets/e1f13412-3462-4697-b9bd-a4abb78fd4f7" />

Pantalla de Bienvenida: Es la prueba final del acceso exitoso, donde usamos la función "Intent" de Android para pasar el nombre del usuario entre pantallas y mostrar un saludo personalizado después de validar los datos en el servidor.

<img width="635" height="1354" alt="Image" src="https://github.com/user-attachments/assets/2db44619-271f-40dc-9c1a-539d202d175c" />

Pantalla de Registro: En esta sección la app recolecta los datos del usuario y los envía al servidor para almacenarlos permanentemente en nuestra base de datos SQLite, asegurando que la cuenta exista la próxima vez que intente entrar.

<img width="630" height="1354" alt="Image" src="https://github.com/user-attachments/assets/aecaf6d7-9675-415f-b69a-cc581aea89b9" />

Consola del Servidor: Aquí podemos ver el cerebro del proyecto funcionando dentro de Docker, registrando en tiempo real cada vez que la aplicación se conecta o envía datos con éxito.

<img width="918" height="172" alt="Image" src="https://github.com/user-attachments/assets/2bd17ee0-a3d9-4da8-ac3d-2a2ac3ed3d12" />

Pantalla de Error de Red: En esta prueba apagamos el servidor para demostrar que la app es capaz de detectar la falta de conexión y avisar al usuario rápidamente, gracias a que configuramos un tiempo de espera de solo 5 segundos para que no se quede congelada.

<img width="628" height="1353" alt="Image" src="https://github.com/user-attachments/assets/1def9ff2-300d-4589-886a-2d794dd0c772" />
