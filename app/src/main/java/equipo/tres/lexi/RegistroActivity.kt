package equipo.tres.lexi

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import equipo.tres.lexi.conexion.ConexionSQLiteHelper
import equipo.tres.lexi.ui.login.LoginActivity
import equipo.tres.lexi.utilidadesSQLite.Utilidades

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val btn_back = findViewById(R.id.btn_back) as Button
        val btnCrearCuenta = findViewById(R.id.crear_cuenta) as Button

        btn_back.setOnClickListener {
            super.onBackPressed()
        }

        btnCrearCuenta.setOnClickListener {
            registrarUsuario()
        }
    }

    private fun registrarUsuario() {

        val txtName = findViewById(R.id.name) as EditText
        val txtPassword = findViewById(R.id.password) as EditText
        val txtPasswordConfirm = findViewById(R.id.passwordConfirm) as EditText
        val txtEmail = findViewById(R.id.email) as EditText

        val conn = ConexionSQLiteHelper(this)

        val db = conn.writableDatabase

        val values = ContentValues().apply {
            put(Utilidades.TablaUsuario.COLUMN_NAME_NOMBRE, txtName.text.toString())
            put(Utilidades.TablaUsuario.COLUMN_NAME_PASSWORD, txtPassword.text.toString())
            put(Utilidades.TablaUsuario.COLUMN_NAME_CORREO, txtEmail.text.toString())
        }

        val newRowId = db?.insert(Utilidades.TablaUsuario.TABLE_NAME, null, values)

        Toast.makeText(this, "Se ha registrado el usuario", Toast.LENGTH_LONG).show()

        db.close()

        var intent: Intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}


