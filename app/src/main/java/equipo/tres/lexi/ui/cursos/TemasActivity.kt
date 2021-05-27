package equipo.tres.lexi.ui.cursos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridView
import com.google.android.material.bottomnavigation.BottomNavigationView
import equipo.tres.lexi.R
import equipo.tres.lexi.data.cursos.Tema
import equipo.tres.lexi.ui.home.HomeActivity
import equipo.tres.lexi.ui.perfil.PerfilActivity

class TemasActivity : AppCompatActivity() {
    var adapter: TemaAdapter? = null
    var temas = ArrayList<Tema>()

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
        setContentView(R.layout.activity_temas)
        val btn_back = findViewById(R.id.btn_back) as Button
        var gridView: GridView = findViewById(R.id.gridView) as GridView
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(nav)
        cargarTemas()

        adapter = TemaAdapter(this, temas)
        gridView.adapter = adapter

        btn_back.setOnClickListener {
            super.onBackPressed()
            finish()
        }
    }


    fun cargarTemas(){
        temas.add(
            Tema(
                R.drawable.negocios, "Negocios",
                R.drawable.bgn_button_gradient
            )
        )
        temas.add(
            Tema(
                R.drawable.peliculas, "Películas",
                R.drawable.bgn_button_gradient2
            )
        )
        temas.add(
            Tema(
                R.drawable.deportes, "Deportes",
                R.drawable.bgn_button_gradient4
            )
        )
        temas.add(
            Tema(
                R.drawable.musica, "Música",
                R.drawable.bgn_button_gradient3
            )
        )
        temas.add(
            Tema(
                R.drawable.gastronomia, "Gastronomía",
                R.drawable.bgn_button_gradient5
            )
        )
        temas.add(
            Tema(
                R.drawable.historia, "Historia",
                R.drawable.bgn_button_gradient
            )
        )
        temas.add(
            Tema(
                R.drawable.moda, "Moda",
                R.drawable.bgn_button_gradient2
            )
        )
        temas.add(
            Tema(
                R.drawable.tradiciones, "Tradiciones",
                R.drawable.bgn_button_gradient4
            )
        )
    }
}