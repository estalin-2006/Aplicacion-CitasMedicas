    package com.estalin.citasmedicasapp.data

    import android.content.Context
    import androidx.room.Database
    import androidx.room.Room
    import androidx.room.RoomDatabase
    import com.estalin.citasmedicasapp.data.dao.*
    import com.estalin.citasmedicasapp.data.entity.*
    import androidx.sqlite.db.SupportSQLiteDatabase
    import kotlinx.coroutines.android.asCoroutineDispatcher
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.launch





    @Database(
        entities = [
            UsuarioEntity::class,
            PacienteEntity::class,
            MedicoEntity::class,
            EspecialidadEntity::class,
            CitaEntity::class,
            HistorialEntity::class
        ],
        version = 1
    )
    abstract class AppDatabase : RoomDatabase() {

        // ✅ AQUÍ debe ir usuarioDao
        abstract fun usuarioDao(): UsuarioDao

        abstract fun pacienteDao(): PacienteDao
        abstract fun medicoDao(): MedicoDao
        abstract fun citaDao(): CitaDao
        abstract fun historialDao(): HistorialDao

        companion object {
            @Volatile private var INSTANCE: AppDatabase? = null

            fun getDatabase(context: Context): AppDatabase {
                return INSTANCE ?: synchronized(this) {
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "citas_db"
                    )
                        .addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)

                                CoroutineScope(Dispatchers.IO).launch {
                                    getDatabase(context).usuarioDao().insert(
                                        UsuarioEntity(
                                            nombre = "Admin",
                                            email = "admin@gmail.com",
                                            password = "1234"
                                        )
                                    )
                                }
                            }
                        })
                        .build().also { INSTANCE = it }
                }
            }
        }
    }