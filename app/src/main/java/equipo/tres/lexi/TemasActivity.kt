package equipo.tres.lexi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridView
import android.widget.ListView
import equipo.tres.lexi.ui.Tema

class TemasActivity : AppCompatActivity() {
    var adapter: TemaAdapter? = null
    var temas = ArrayList<Tema>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temas)
        val btn_back = findViewById(R.id.btn_back) as Button
        var gridView: GridView = findViewById(R.id.gridView) as GridView

        cargarTemas()

        adapter = TemaAdapter(this, temas)
        gridView.adapter = adapter

        btn_back.setOnClickListener {
            super.onBackPressed()

        }
    }


    fun cargarTemas(){
        temas.add(Tema(R.drawable.negocios, "Negocios",R.drawable.bgn_button_gradient))
        temas.add(Tema(R.drawable.peliculas, "Películas",R.drawable.bgn_button_gradient2))
        temas.add(Tema(R.drawable.deportes, "Deportes",R.drawable.bgn_button_gradient4))
        temas.add(Tema(R.drawable.musica, "Música",R.drawable.bgn_button_gradient3))
        temas.add(Tema(R.drawable.gastronomia, "Gastronomía",R.drawable.bgn_button_gradient5))
        temas.add(Tema(R.drawable.historia, "Historia",R.drawable.bgn_button_gradient))
        temas.add(Tema(R.drawable.moda, "Moda",R.drawable.bgn_button_gradient2))
        temas.add(Tema(R.drawable.tradiciones, "Tradiciones",R.drawable.bgn_button_gradient4))
    }
}