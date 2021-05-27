package equipo.tres.lexi.ui.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import equipo.tres.lexi.data.cursos.Nivel
import equipo.tres.lexi.R
import equipo.tres.lexi.data.cursos.Curso
import equipo.tres.lexi.ui.cursos.MisCursosActivity
import equipo.tres.lexi.ui.perfil.PerfilActivity

class HomeActivity : AppCompatActivity() {
    var adapter: CursoAdapter? = null
    var cursos = ArrayList<Curso>()
    private lateinit var usuario: FirebaseAuth
    private lateinit var storage: FirebaseFirestore


    private val nav = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_inicio -> {
                val intent = Intent(this@HomeActivity, HomeActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_cursos -> {
                val intent = Intent(this@HomeActivity, MisCursosActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_perfil -> {
                val intent = Intent(this@HomeActivity, PerfilActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
        }
        false

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        usuario = Firebase.auth
        storage = FirebaseFirestore.getInstance()
        val gvCursos: GridView = findViewById(R.id.gv_cursos)
        val tv_bienvenida: TextView = findViewById(R.id.tv_bienvenida)
        val bundle = intent.extras

//            val nombre = storage.
//
//            tv_bienvenida.append(nombre)


        cargarCursos()
        adapter =
            CursoAdapter(this, cursos,usuario,storage)
        gvCursos.adapter = adapter

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(nav)






    }



    private fun cargarCursos() {
        //var nombre: String, var image: Int, var  niveles: ArrayList<Nivel>, var progreso: Int
        cursos.add(
            Curso(
                "Inglés",
                R.drawable.inglaterra,
                arrayListOf<Nivel>(),
                "0"
            )
        )
        cursos.add(
            Curso(
                "Francés",
                R.drawable.francia,
                arrayListOf<Nivel>(),
                "0"
            )
        )
        cursos.add(
            Curso(
                "Alemán",
                R.drawable.alemania,
                arrayListOf<Nivel>(),
                "0"
            )
        )
        cursos.add(
            Curso(
                "Italiano",
                R.drawable.italia,
                arrayListOf<Nivel>(),
                "0"
            )
        )
        cursos.add(
            Curso(
                "Ruso",
                R.drawable.rusia,
                arrayListOf<Nivel>(),
                "0"
            )
        )
        cursos.add(
            Curso(
                "Japonés",
                R.drawable.japon,
                arrayListOf<Nivel>(),
                "0"
            )
        )
        cursos.add(
            Curso(
                "Coreano",
                R.drawable.corea,
                arrayListOf<Nivel>(),
                "0"
            )
        )
        cursos.add(
            Curso(
                "Mandarín",
                R.drawable.china,
                arrayListOf<Nivel>(),
                "0"
            )
        )
    }

    class CursoAdapter : BaseAdapter {
        var cursos = ArrayList<Curso>()
        var contexto: Context? = null
        private lateinit var usuario: FirebaseAuth
        private lateinit var storage: FirebaseFirestore

        constructor(context: Context, cursos: ArrayList<Curso>, usuario:FirebaseAuth, storage:FirebaseFirestore) {
            this.contexto = context
            this.cursos = cursos
            this.usuario=usuario
            this.storage=storage
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var curso = cursos[position]
            var inflator =
                contexto!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var vista = inflator.inflate(R.layout.cursos, null)

            var imagen = vista.findViewById(R.id.iv_imagenCurso) as ImageView
            var nombre = vista.findViewById(R.id.tv_nombreCurso) as TextView

            imagen.setImageResource(curso.image)
            nombre.setText(curso.nombre)


            imagen.setOnClickListener() {
                var intent = Intent(contexto, NivelesActivity::class.java)
                intent.putExtra("idioma", curso.nombre)
                intent.putExtra("imagen", curso.image)
                intent.putExtra("niveles", curso.niveles)
                intent.putExtra("progreso", (curso.progreso))
                updateProgreso(curso)
                contexto!!.startActivity(intent)


            }
            return vista
        }

        fun updateProgreso(curso:Curso){
            val progreso = hashMapOf(
                "idioma" to curso.nombre,
                "nivel" to "3"
            )
            storage.collection("progreso")
                .document(this.usuario.currentUser!!.email.toString())
                .update(progreso as Map<String, Any>)
                .addOnSuccessListener {
                    Toast.makeText(this.contexto, "Se ha actualizado correctamente el progreso del usuario.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{
                    Toast.makeText(this.contexto, "No se ha actualizado correctamente el progreso del usuario.", Toast.LENGTH_SHORT).show()
                }
        }

        override fun getItem(position: Int): Any {
            return cursos[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return cursos.size
        }
    }
}

