package equipo.tres.lexi.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import equipo.tres.lexi.R
import equipo.tres.lexi.data.cursos.Nivel
import equipo.tres.lexi.ui.cursos.MisCursosActivity
import equipo.tres.lexi.ui.perfil.PerfilActivity
import kotlinx.android.synthetic.main.activity_niveles.*

class NivelesActivity : AppCompatActivity() {

    var adapter: NivelAdapter? = null
    var niveles = ArrayList<Nivel>()

    var idioma: String = ""
    var leccion: String = ""

    private lateinit var storage: FirebaseFirestore
    private lateinit var usuario: FirebaseAuth

    private val nav = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_inicio -> {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_cursos -> {
                val intent = Intent(this, MisCursosActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_perfil -> {
                val intent = Intent(this, PerfilActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
        }
        false

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_niveles)

        val btn_back = findViewById(R.id.btn_back) as Button
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(nav)

        idioma = getIntent().getStringExtra("idioma")!!;

        btn_back.setOnClickListener {
            super.onBackPressed()
        }
        //homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val gvNiveles: GridView = findViewById(R.id.gv_niveles)

        storage = FirebaseFirestore.getInstance()
        usuario = FirebaseAuth.getInstance()

        cargarNiveles()
        adapter =
            NivelAdapter(this, niveles, idioma)
        gvNiveles.adapter = adapter
    }

    private fun cargarNiveles() {
        /*niveles.add(
            Nivel(
                "Nivel 1 Básico",
                R.drawable.basico,
                R.string.lorem_ipsum,
                "basico",
                66
            )
        )
        niveles.add(
            Nivel(
                "Nivel 2 Básico",
                R.drawable.frases,
                R.string.lorem_ipsum,
                "basico",
                0
            )
        )
        niveles.add(
            Nivel(
                "Nivel 3 Básico",
                R.drawable.profesiones,
                R.string.lorem_ipsum,
                "basico",
                0
            )
        )*/

        storage.collection("cursos").document(idioma)
            .collection("niveles")
            .get()
            .addOnSuccessListener {
                it.forEach {

                    println(it)
                    niveles.add(
                        Nivel(
                            it.getString("nombre")!!,
                            R.drawable.profesiones,
                            (it.get("intro") as List<String>?)!!,
                            it.id,
                            0
                        )
                    )
                }
                adapter = NivelAdapter(applicationContext, niveles, idioma)
                gv_niveles.adapter = adapter
            }
            .addOnFailureListener {
                Toast.makeText(getBaseContext(), "Error: intente de nuevo", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    class NivelAdapter : BaseAdapter {
        var niveles = ArrayList<Nivel>()
        var contexto: Context? = null
        var idioma: String = ""

        constructor(context: Context, niveles: ArrayList<Nivel>, idioma: String) {
            this.contexto = context
            this.niveles = niveles
            this.idioma = idioma

        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var nivel = niveles[position]
            var inflator =
                contexto!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var vista = inflator.inflate(R.layout.nivel, null)

            var imagen = vista.findViewById(R.id.iv_imagenNivel) as ImageView
            var nombre = vista.findViewById(R.id.tv_nombre) as TextView

            imagen.setImageResource(nivel.image)
            nombre.setText(nivel.nombre)


            imagen.setOnClickListener() {
                var intent = Intent(contexto, IntroductionActivity::class.java)

                intent.putExtra("idioma", idioma)
                intent.putExtra("nombre", nivel.nombre)

                intent.putStringArrayListExtra(
                    "introduccion",
                    nivel.introduccion as java.util.ArrayList<String>
                )
                intent.putExtra("nivel", nivel.nivel)
                intent.putExtra("progreso", nivel.progreso)

                contexto!!.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            }
            return vista
        }

        override fun getItem(position: Int): Any {
            return niveles[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return niveles.size
        }
    }
}