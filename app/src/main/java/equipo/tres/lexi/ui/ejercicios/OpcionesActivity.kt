package equipo.tres.lexi.ui.ejercicios

import android.content.Intent
import android.os.Bundle
import android.view.View.OnTouchListener
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import equipo.tres.lexi.R
import kotlinx.android.synthetic.main.activity_opciones.*


class OpcionesActivity : AppCompatActivity() {

    private lateinit var storage: FirebaseFirestore
    private lateinit var usuario: FirebaseAuth

    private var respuestaCorrecta = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opciones)

        val btn_back = findViewById<Button>(R.id.btn_back)
        val btn_nivel= findViewById<Button>(R.id.btn_continuar)

        var idioma = intent.getStringExtra("idioma")
        var nombre = intent.getStringExtra("nombre")
        var leccion = intent.getStringExtra("leccion")

        storage = FirebaseFirestore.getInstance()
        usuario = FirebaseAuth.getInstance()

        cargarLeccion(idioma, nombre, leccion)

        btn_back.setOnClickListener {
            super.onBackPressed()
            finish()
        }

        btn_nivel.setOnClickListener {
            if (btn_opcion1.isPressed || btn_opcion2.isPressed || btn_opcion3.isPressed || btn_opcion4.isPressed) {

                var respuesta = ""

                if (btn_opcion1.isPressed) {
                    respuesta = btn_opcion1.text.toString()
                } else if (btn_opcion2.isPressed) {
                    respuesta = btn_opcion2.text.toString()
                } else if (btn_opcion3.isPressed) {
                    respuesta = btn_opcion3.text.toString()
                } else if (btn_opcion4.isPressed) {
                    respuesta = btn_opcion4.text.toString()
                }

                if (respuestaCorrecta.equals(respuesta, true)) {
                    Toast.makeText(baseContext, "Correcto", Toast.LENGTH_SHORT).show()

                    var intent = Intent(this, CompletarActivity::class.java)
                    intent.putExtra("idioma", idioma)
                    intent.putExtra("nombre", nombre)
                    intent.putExtra("leccion", leccion)
                    actualizarProgreso(idioma!!)
                    this.startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(baseContext, "Incorrecto", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(baseContext, "Seleciona una opcion", Toast.LENGTH_SHORT).show()
            }
        }


        btn_opcion1.setOnTouchListener { v, event ->
            btn_opcion1.isPressed = true
            btn_opcion2.isPressed = false
            btn_opcion3.isPressed = false
            btn_opcion4.isPressed = false
            true
        }

        btn_opcion2.setOnTouchListener { v, event ->
            btn_opcion1.isPressed = false
            btn_opcion2.isPressed = true
            btn_opcion3.isPressed = false
            btn_opcion4.isPressed = false
            true
        }

        btn_opcion3.setOnTouchListener { v, event ->
            btn_opcion1.isPressed = false
            btn_opcion2.isPressed = false
            btn_opcion3.isPressed = true
            btn_opcion4.isPressed = false
            true
        }

        btn_opcion4.setOnTouchListener { v, event ->
            btn_opcion1.isPressed = false
            btn_opcion2.isPressed = false
            btn_opcion3.isPressed = false
            btn_opcion4.isPressed = true
            true
        }
    }

    private fun actualizarProgreso(idioma: String) {
        val idioma:String=idioma

        storage.collection("progreso").whereEqualTo("usuario", usuario.currentUser?.email)
            .whereEqualTo("idioma",idioma)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    storage.collection("progreso").document(document.id)
                        .update("progreso", FieldValue.increment(4))
                    Toast.makeText(this, "¡Progreso guardado!", Toast.LENGTH_SHORT)
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Error: " + e.toString(),
                    Toast.LENGTH_SHORT
                )
            }
    }

    private fun cargarLeccion(idioma: String?, nombre: String?, leccion: String?) {
        if (idioma != null && nombre != null && leccion != null) {
            storage.collection("cursos").document(idioma)
                .collection("niveles").document(leccion)
                .collection("lecciones").document("leccion2")
                .get()
                .addOnSuccessListener {

                    btn_opcion1.text = it.getString("opcion1")
                    btn_opcion2.text = it.getString("opcion2")
                    btn_opcion3.text = it.getString("opcion3")
                    btn_opcion4.text = it.getString("opcion4")
                    palabra.text = it.getString("palabra")
                    respuestaCorrecta = it.getString("respuesta")!!

                }
                .addOnFailureListener {
                    Toast.makeText(baseContext, "Error: intente de nuevo", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }
}