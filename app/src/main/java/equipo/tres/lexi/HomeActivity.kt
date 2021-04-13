package equipo.tres.lexi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import equipo.tres.lexi.ui.home.HomeFragment

class HomeActivity : AppCompatActivity() {
    var adapter: CursoAdapter? = null
    var cursos = ArrayList<Curso>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val gvCursos: GridView = findViewById(R.id.gv_cursos)

        cargarCursos()
        adapter = CursoAdapter(this, cursos)
        gvCursos.adapter = adapter
    }

    private fun cargarCursos() {
        //var nombre: String, var image: Int, var  niveles: ArrayList<Nivel>, var progreso: Int
        cursos.add(Curso("Inglés", R.drawable.inglaterra, arrayListOf<Nivel>(), "0"))
        cursos.add(Curso("Francés", R.drawable.francia, arrayListOf<Nivel>(), "0"))
        cursos.add(Curso("Alemán", R.drawable.alemania, arrayListOf<Nivel>(), "0"))
        cursos.add(Curso("Italiano", R.drawable.italia, arrayListOf<Nivel>(), "0"))
        cursos.add(Curso("Ruso", R.drawable.rusia, arrayListOf<Nivel>(), "0"))
        cursos.add(Curso("Japonés", R.drawable.japon, arrayListOf<Nivel>(), "0"))
        cursos.add(Curso("Coreano", R.drawable.corea, arrayListOf<Nivel>(), "0"))
        cursos.add(Curso("Mandariín", R.drawable.china, arrayListOf<Nivel>(), "0"))
    }

    class CursoAdapter : BaseAdapter {
        var cursos = ArrayList<Curso>()
        var contexto: Context? = null

        constructor(context: Context, cursos: ArrayList<Curso>) {
            this.contexto = context
            this.cursos = cursos
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
                intent.putExtra("nombre", curso.nombre)
                intent.putExtra("imagen", curso.image)
                intent.putExtra("niveles", curso.niveles)
                intent.putExtra("progreso", (curso.progreso))
                contexto!!.startActivity(intent)
            }
            return vista
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

