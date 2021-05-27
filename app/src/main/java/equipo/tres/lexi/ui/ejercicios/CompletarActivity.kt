package equipo.tres.lexi.ui.ejercicios

import android.content.Intent
import android.graphics.DrawFilter
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import equipo.tres.lexi.R
import equipo.tres.lexi.ui.home.NivelesActivity
import kotlinx.android.synthetic.main.activity_completar.*


class CompletarActivity : AppCompatActivity() {

    private lateinit var storage: FirebaseFirestore
    private lateinit var usuario: FirebaseAuth

    var respuestasCorrectas: List<String> = emptyList()
    var frases: List<String> = emptyList()

    var myEditTextList = ArrayList<EditText>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_completar)

        val btn_back = findViewById(R.id.btn_back) as Button
        val btn_nivel = findViewById(R.id.btn_continuar) as Button

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

        btn_nivel.setOnClickListener() {

            var campoVacios = false

            val myEditTextList = ArrayList<EditText>()

            for (i in 0 until linearLayoutRespuestas.getChildCount()) {
                if (linearLayoutRespuestas.getChildAt(i) is LinearLayout) {

                    if (((linearLayoutRespuestas.getChildAt(i) as LinearLayout).getChildAt(1) as EditText).text.isNullOrEmpty()) {
                        campoVacios = true
                    }

                    myEditTextList.add(
                        (linearLayoutRespuestas.getChildAt(i) as LinearLayout).getChildAt(
                            1
                        ) as EditText
                    )
                }
            }

            if (!campoVacios) {
                var correctos = true

                for (i in 0 until respuestasCorrectas.size) {
                    if (!myEditTextList.get(i).text.toString()
                            .equals(respuestasCorrectas.get(i), true)
                    ) {
                        correctos = false
                    }
                }

                if (correctos) {
                    Toast.makeText(baseContext, "Correcto", Toast.LENGTH_SHORT).show()

                    var intent = Intent(this, NivelesActivity::class.java)
                    intent.putExtra("idioma", idioma)
                    intent.putExtra("nombre", nombre)
                    intent.putExtra("leccion", leccion)
                    actualizarProgreso(idioma!!)
                    this!!.startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        baseContext,
                        "Incorrecto. Revisa los campos.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(baseContext, "Hay campos vacios", Toast.LENGTH_SHORT).show()
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
                .collection("lecciones").document("leccion3")
                .get()
                .addOnSuccessListener {

                    frases = it.get("frases") as List<String>
                    respuestasCorrectas = it.get("respuestas") as List<String>

                }
                .addOnFailureListener {
                    Toast.makeText(baseContext, "Error: intente de nuevo", Toast.LENGTH_SHORT)
                        .show()
                }

            var contador = 1

            frases.forEach {
                val array = it.split("-")

                when (contador) {
                    1 -> {
                        frase1_1.text = array[0]
                        frase1_2.text = array[1]
                    }

                    2 -> {
                        frase2_1.text = array[0]
                        frase2_2.text = array[1]
                    }

                    3 -> {
                        frase3_1.text = array[0]
                        frase3_2.text = array[1]
                    }

                    4 -> {
                        frase4_1.text = array[0]
                        frase4_2.text = array[1]
                    }

                    5 -> {
                        frase5_1.text = array[0]
                        frase5_2.text = array[1]
                    }

                    6 -> {
                        frase6_1.text = array[0]
                        frase6_2.text = array[1]
                    }
                }
            }
        }
    }
}