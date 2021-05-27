package equipo.tres.lexi.ui.ejercicios

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import equipo.tres.lexi.R
import equipo.tres.lexi.data.cursos.Nivel
import equipo.tres.lexi.ui.home.NivelesActivity
import kotlinx.android.synthetic.main.activity_traducir.*

class TraducirActivity : AppCompatActivity() {

    private lateinit var storage: FirebaseFirestore
    private lateinit var usuario: FirebaseAuth

    private var respuestaCorrecta = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_traducir)

        val btn_back = findViewById(R.id.btn_back) as Button
        val btn_nivel = findViewById(R.id.btn_continuar) as Button

        var idioma = getIntent().getStringExtra("idioma")
        var nombre = getIntent().getStringExtra("nombre")
        var leccion = getIntent().getStringExtra("leccion")

        storage = FirebaseFirestore.getInstance()
        usuario = FirebaseAuth.getInstance()

        cargarLeccion(idioma, nombre, leccion)

        btn_back.setOnClickListener {
            super.onBackPressed()

            finish()
        }

        btn_nivel.setOnClickListener() {
            if (respuestaCorrecta.equals(respuesta.text.toString(), true)) {
                Toast.makeText(getBaseContext(), "Correcto", Toast.LENGTH_SHORT).show()

                var intent = Intent(this, OpcionesActivity::class.java)
                intent.putExtra("idioma", idioma)
                intent.putExtra("nombre", nombre)
                intent.putExtra("leccion", leccion)
                actualizarProgreso(idioma!!)
                this!!.startActivity(intent)
                finish()
            } else {
                Toast.makeText(getBaseContext(), "Incorrecto", Toast.LENGTH_SHORT).show()
            }
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
                .collection("lecciones").document("leccion1")
                .get()
                .addOnSuccessListener {

                    frase.text = it.getString("frase")
                    respuestaCorrecta = it.getString("respuesta")!!

                }
                .addOnFailureListener {
                    Toast.makeText(getBaseContext(), "Error: intente de nuevo", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }
}