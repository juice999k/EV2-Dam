package com.example.lexis.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UsuarioEntity::class], version = 1, exportSchema = false)
abstract class LexisDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var instancia: LexisDatabase? = null

        fun obtener(contexto: Context): LexisDatabase =
            instancia ?: synchronized(this) {
                instancia ?: Room.databaseBuilder(
                    contexto.applicationContext,
                    LexisDatabase::class.java,
                    "lexis.db"
                ).build().also { instancia = it }
            }
    }
}
