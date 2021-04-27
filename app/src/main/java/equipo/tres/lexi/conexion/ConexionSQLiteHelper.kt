package equipo.tres.lexi.conexion

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import equipo.tres.lexi.utilidadesSQLite.Utilidades.CREAR_TABLA_USUARIOS
import equipo.tres.lexi.utilidadesSQLite.Utilidades.DROP_TABLA_USUARIOS

class ConexionSQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREAR_TABLA_USUARIOS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_TABLA_USUARIOS)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "lexi.db"
    }
}