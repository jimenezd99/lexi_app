package equipo.tres.lexi.utilidadesSQLite

import android.provider.BaseColumns

object Utilidades {

    const val CREAR_TABLA_USUARIOS : String = "CREATE TABLE ${TablaUsuario.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
            "${TablaUsuario.COLUMN_NAME_NOMBRE} TEXT, " +
            "${TablaUsuario.COLUMN_NAME_PASSWORD} TEXT, " +
            "${TablaUsuario.COLUMN_NAME_CORREO} TEXT)"

    const val DROP_TABLA_USUARIOS : String  = "DROP TABLE IF EXISTS ${TablaUsuario.TABLE_NAME}"

    object TablaUsuario: BaseColumns{
        const val TABLE_NAME = "usuarios"
        const val COLUMN_NAME_NOMBRE = "nombre"
        const val COLUMN_NAME_PASSWORD = "password"
        const val COLUMN_NAME_CORREO = "correo"
    }
}