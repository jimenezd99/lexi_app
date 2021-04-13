package equipo.tres.lexi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView

class MisCursosActivity : AppCompatActivity() {

    var adapter: CursoAdapter? = null
    var cursos = ArrayList<MiCurso>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_cursos)
        var listView: ListView = findViewById(R.id.listView) as ListView
        val btn_back = findViewById(R.id.btn_back) as Button

        cargarCursos()

        adapter = CursoAdapter(this, cursos)
        listView.adapter = adapter

        btn_back.setOnClickListener {
            super.onBackPressed()

        }


    }

    fun cargarCursos(){
        cursos.add(MiCurso(R.drawable.inglaterra, "Nivel 10","Inglés", "Progreso: 58%"))
        cursos.add(MiCurso(R.drawable.francia, "Nivel 1","Francés", "Progreso: 10%"))
    }
}