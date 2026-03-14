from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import sqlite3

app = FastAPI()

# --- Configuración de la Base de Datos ---
def init_db():
    # Esto crea un archivo llamado "usuarios.db" si no existe
    conn = sqlite3.connect("usuarios.db")
    cursor = conn.cursor()
    cursor.execute("""
        CREATE TABLE IF NOT EXISTS users (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            username TEXT UNIQUE,
            password TEXT
        )
    """)
    conn.commit()
    conn.close()

# Ejecutamos la creación de la tabla al iniciar
init_db()

class User(BaseModel):
    username: str
    password: str

@app.get("/")
def read_root():
    return {"message": "La API con SQLite está funcionando"}

@app.post("/register")
def register_user(user: User):
    try:
        conn = sqlite3.connect("usuarios.db")
        cursor = conn.cursor()
        cursor.execute("INSERT INTO users (username, password) VALUES (?, ?)", (user.username, user.password))
        conn.commit()
        conn.close()
        return {"message": "Registro exitoso en SQLite"}
    except sqlite3.IntegrityError:
        # Esto sucede si el username ya existe (por el UNIQUE en la tabla)
        raise HTTPException(status_code=400, detail="Usuario ya existe")

@app.post("/login")
def login_user(user: User):
    conn = sqlite3.connect("usuarios.db")
    cursor = conn.cursor()
    cursor.execute("SELECT * FROM users WHERE username = ? AND password = ?", (user.username, user.password))
    result = cursor.fetchone()
    conn.close()
    
    if result:
        return {"message": f"Bienvenido {user.username}"}
    raise HTTPException(status_code=401, detail="Credenciales incorrectas")