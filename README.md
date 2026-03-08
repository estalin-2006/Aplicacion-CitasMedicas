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

#  Relaciones del Sistema

Paciente → puede tener **muchas citas**

Médico → puede tener **muchas citas**

Paciente → puede tener **muchos registros de historial clínico**

Médico → puede registrar **historial clínico**

---

#  Capturas de la Aplicación

## Pantalla principal

<img width="402" height="841" alt="image" src="https://github.com/user-attachments/assets/d1690777-8970-4a82-a65c-86f77a077087" />


---

## Registro de pacientes

<img width="404" height="786" alt="image" src="https://github.com/user-attachments/assets/ed1d3105-6e83-4461-bc6b-e952167b28f7" />


## panel de mdinistrador 

<img width="392" height="767" alt="image" src="https://github.com/user-attachments/assets/c40765a9-4e63-48da-89d8-546090a9abfa" />
##panel de gesyiona usuarios 
<img width="396" height="749" alt="image" src="https://github.com/user-attachments/assets/6fafb10b-aa38-49d4-bbfe-93d3d2c2c214" />

## Lista de citas de paciente 
<img width="390" height="758" alt="image" src="https://github.com/user-attachments/assets/b85d9572-fbb1-4f6c-9b1d-e267b2748761" />


## agendar cita  paciente 
<img width="404" height="648" alt="image" src="https://github.com/user-attachments/assets/af282ec1-86e8-4d64-b9aa-02eb42c82ad1" />

## lista citas del meidoc
<img width="378" height="886" alt="image" src="https://github.com/user-attachments/assets/119ccf26-f8e0-4cea-822b-88cc40cc9cdc" />

---

# ⚙Tecnologías Utilizadas

* **Android Studio**
* **Kotlin**
* **MVVM Architecture**
* **Room Database**
* **Supabase**
* **PostgreSQL**
* **REST API**

---

#  Instalación de la Aplicación

1. Descargar el archivo APK desde el repositorio
2. Instalar el APK en un dispositivo Android
3. Abrir la aplicación
4. Registrar usuario o iniciar sesión

---

# 📲 APK de Instalación

El archivo APK generado se encuentra en:

```
"C:\Users\Personal\OneDrive\Escritorio\Aplicacion-CitasMedicas\app\release\app-release.apk"

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




