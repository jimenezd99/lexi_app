package equipo.tres.lexi.ui.cursos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import equipo.tres.lexi.ui.home.HomeActivity
import equipo.tres.lexi.R
import equipo.tres.lexi.data.cursos.MiCurso
import equipo.tres.lexi.ui.perfil.PerfilActivity

class MisCursosActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var storage:FirebaseFirestore
    var adapter: CursoAdapter? = null
    var cursos = ArrayList<MiCurso>()


    private val nav = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_inicio -> {
                val intent = Intent(this@MisCursosActivity, HomeActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_cursos -> {
                val intent = Intent(this@MisCursosActivity, MisCursosActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_perfil -> {
                val intent = Intent(this@MisCursosActivity, PerfilActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
        }
        false

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_cursos)
        auth = Firebase.auth
        storage= FirebaseFirestore.getInstance()
        var listView: ListView = findViewById(R.id.listView) as ListView
        val btn_back = findViewById(R.id.btn_back) as Button
        val tv_nombre=findViewById(R.id.cursos_de) as TextView
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(nav)
//        cargarDatos(storage)
        cargarCursos()

        adapter = CursoAdapter(this, cursos)
        listView.adapter = adapter

        btn_back.setOnClickListener {
            super.onBackPressed()
            finish()
        }


    }

    fun cargarCursos(){
        cursos.add(
            MiCurso(
                R.drawable.inglaterra,
                "Nivel 10",
                "Inglés",
                "Progreso: 58%"
            )
        )
        cursos.add(
            MiCurso(
                R.drawable.francia,
                "Nivel 1",
                "Francés",
                "Progreso: 10%"
            )
        )
    }

    private fun cargarDatos(storage: FirebaseFirestore, tv_nombre: TextView, tvProgreso: TextView){
        storage.collection("usuarios").whereEqualTo("email", auth.currentUser?.email)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Toast.makeText(this,"${document.id} => ${document.data}", Toast.LENGTH_SHORT)
//                    val nombre:String=document.data.get("nombre").toString()
                    val progreso:String=document.data.get("progreso").toString()
                    val idioma:String=document.data.get("idioma").toString()
                    tv_nombre.setText("Cursos de ${idioma}")
//                    val valor= progreso.split(".")[0]
                    tvProgreso.setText("Progreso  ${progreso.toString()} %")
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error getting documents.$exception", Toast.LENGTH_SHORT)
            }

    }
}