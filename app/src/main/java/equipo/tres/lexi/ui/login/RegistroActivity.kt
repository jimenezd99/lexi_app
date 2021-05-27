package equipo.tres.lexi.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import equipo.tres.lexi.R


class RegistroActivity : AppCompatActivity() {
    private lateinit var usuario: FirebaseAuth
    private lateinit var storage: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        usuario = Firebase.auth
        storage = FirebaseFirestore.getInstance()
        val btn_back = findViewById(R.id.btn_back) as Button

        val btn_registrar = findViewById(R.id.btn_crear_cuenta) as Button

        btn_back.setOnClickListener {
            super.onBackPressed()
        }
        btn_registrar.setOnClickListener {
            validaRegistro()
        }
    }

    private fun validaRegistro() {
        val et_name: EditText = findViewById(R.id.name)
        val et_email: EditText = findViewById(R.id.email)
        val et_pass1: EditText = findViewById(R.id.password)
        val et_pass2: EditText = findViewById(R.id.passwordConfirm)

        var nombre: String = et_name.text.toString()
        var correo: String = et_email.text.toString()
        var pass1: String = et_pass1.text.toString()
        var pass2: String = et_pass2.text.toString()

        if (!nombre.isNullOrBlank() && !correo.isNullOrBlank() && !pass1.isNullOrBlank() && !pass2.isNullOrBlank()) {

            if (pass1 == pass2) {
                registrarFirebase(correo, pass1, nombre)
            } else {
                Toast.makeText(baseContext, "La contraseñas no coinciden.", Toast.LENGTH_SHORT)
                    .show()
            }

        } else {
            Toast.makeText(baseContext, "Ingresar datos.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun registrarFirebase(email: String, password: String, nombre: String) {
        usuario.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = usuario.currentUser
                    registrarDatosUsuario(email, nombre)
//                    updateUI(user)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
//                    updateUI(null)
                }
            }
    }

    private fun registrarDatosUsuario(email: String, nombre: String) {
        val userData = hashMapOf(
            "nombre" to nombre,
            "email" to email
        )
        registrarProgreso(email)
        storage.collection("usuarios")
            .add(userData)
            .addOnSuccessListener {

            }
            .addOnFailureListener {
                Toast.makeText(
                    baseContext,
                    "No se ha guardado correctamente el progreso del usuario.",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}