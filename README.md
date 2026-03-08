#  Aplicación de Citas Médicas

Aplicación móvil desarrollada en **Android Studio** que permite la **gestión de citas médicas entre pacientes y médicos**, incluyendo registro de usuarios, historial clínico y manejo de especialidades médicas.

El sistema está diseñado con **arquitectura MVVM**, base de datos **local (Room)** para funcionamiento **offline** y sincronización con **Supabase (PostgreSQL en la nube)**.

---

#  Objetivo del Proyecto

Desarrollar una aplicación móvil que permita:

* Registrar pacientes
* Registrar médicos
* Gestionar citas médicas
* Registrar historial clínico
* Administrar especialidades médicas
* Almacenar información en la nube
* Permitir funcionamiento offline

---

#  Arquitectura del Sistema

La aplicación utiliza el patrón de arquitectura:

**MVVM (Model – View – ViewModel)**

## Componentes

**View**

* Activities
* Layouts XML
* Interfaz de usuario

**ViewModel**

* Manejo de lógica de negocio
* Comunicación entre UI y repositorio

**Model**

* Entidades de datos
* Clases de dominio

**Repository**

* Gestión de acceso a datos
* Comunicación con base de datos local y remota

---

# 🗄 Base de Datos

La aplicación utiliza:

* **Room Database (Local / Offline)**
* **Supabase PostgreSQL (Base de datos en la nube)**

---

# Diagrama Entidad Relación (DER)

<img width="1577" height="786" alt="image" src="https://github.com/user-attachments/assets/4545a06e-3549-405c-8551-cf777aa861da" />


---

# 📑 Estructura de la Base de Datos

## Tabla: usuarios

| Campo        | Tipo      |
| ------------ | --------- |
| id           | int       |
| nombre       | text      |
| email        | text      |
| password     | text      |
| rol          | text      |
| especialidad | text      |
| codigo       | text      |
| created_at   | timestamp |

Descripción:

Almacena los usuarios del sistema como administradores o médicos.

---

## Tabla: pacientes

| Campo    | Tipo |
| -------- | ---- |
| id       | int  |
| nombre   | text |
| email    | text |
| rol      | text |
| password | text |

Descripción:

Contiene la información de los pacientes registrados.

---

## Tabla: medicos

| Campo        | Tipo      |
| ------------ | --------- |
| id           | int       |
| nombre       | text      |
| email        | text      |
| rol          | text      |
| especialidad | text      |
| created_at   | timestamp |

Descripción:

Guarda los datos de los médicos disponibles para citas.

---

## Tabla: especialidades

| Campo  | Tipo |
| ------ | ---- |
| id     | int  |
| nombre | text |

Descripción:

Define las diferentes áreas médicas.

Ejemplos:

* Cardiología
* Pediatría
* Dermatología

---

## Tabla: citas

| Campo           | Tipo      |
| --------------- | --------- |
| id              | int       |
| paciente_id     | int       |
| medico_id       | int       |
| paciente_nombre | text      |
| medico_nombre   | text      |
| fecha           | text      |
| hora            | text      |
| estado          | text      |
| created_at      | timestamp |

Descripción:

Registra las citas médicas entre pacientes y médicos.

Relaciones:

* paciente_id → pacientes
* medico_id → medicos

---

## Tabla: historial_clinico

| Campo       | Tipo      |
| ----------- | --------- |
| id          | int       |
| paciente_id | int       |
| medico_id   | int       |
| fecha       | text      |
| descripcion | text      |
| created_at  | timestamp |

Descripción:

Guarda el historial médico de los pacientes registrado por los médicos.

---

# 🔗 Relaciones del Sistema

Paciente → puede tener **muchas citas**

Médico → puede tener **muchas citas**

Paciente → puede tener **muchos registros de historial clínico**

Médico → puede registrar **historial clínico**

---

# 📸 Capturas de la Aplicación

## Pantalla principal

![Home](imagenes/home.png)

---

## Registro de pacientes

![Registro](imagenes/registro.png)

---

## Lista de citas

![Lista](imagenes/lista.png)

---

# ⚙ Tecnologías Utilizadas

* **Android Studio**
* **Kotlin**
* **MVVM Architecture**
* **Room Database**
* **Supabase**
* **PostgreSQL**
* **REST API**

---

# 📦 Instalación de la Aplicación

1. Descargar el archivo APK desde el repositorio
2. Instalar el APK en un dispositivo Android
3. Abrir la aplicación
4. Registrar usuario o iniciar sesión

---

# 📲 APK de Instalación

El archivo APK generado se encuentra en:

```
app/build/outputs/apk/debug/app-debug.apk
```

---

#  Ejecución del Proyecto

1. Clonar el repositorio

```
git clone https://github.com/estalin-2006/Aplicacion-CitasMedicas.git
```

2. Abrir el proyecto en **Android Studio**

3. Sincronizar Gradle

4. Ejecutar la aplicación en:

* Emulador Android
* Dispositivo físico

---

#  Autor

**Estalin Tenelema -  Erick Minda**

Proyecto académico desarrollado para la materia de **Desarrollo de Aplicaciones Móviles**.

---




