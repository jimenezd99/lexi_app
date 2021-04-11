package equipo.tres.lexi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import android.widget.ListView

class MisCursosActivity : AppCompatActivity() {

    var adapter: CursoAdapter? = null
    var cursos = ArrayList<Curso>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_cursos)
        var listView: ListView = findViewById(R.id.listView) as ListView
        cargarCursos()

        adapter = CursoAdapter(this, cursos)
        listView.adapter = adapter


    }

    fun cargarCursos(){
        cursos.add(Curso(R.drawable.inglaterra, "Nivel 10","Inglés", "Progreso: 58%"))
        cursos.add(Curso(R.drawable.francia, "Nivel 1","Francés", "Progreso: 10%"))
    }
}