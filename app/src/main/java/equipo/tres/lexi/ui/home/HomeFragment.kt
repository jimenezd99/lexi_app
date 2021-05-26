package equipo.tres.lexi.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import equipo.tres.lexi.*
import equipo.tres.lexi.data.cursos.Curso
import equipo.tres.lexi.data.cursos.Nivel

class HomeFragment : Fragment() {

    //private lateinit var homeViewModel: HomeViewModel
    var adapter: CursoAdapter? = null
    var cursos = ArrayList<Curso>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val gvCursos: GridView = root.findViewById(R.id.gv_cursos)

        cargarCursos()
        adapter = CursoAdapter(this.requireContext(), cursos)
        gvCursos.adapter = adapter

        return root
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
                intent.putExtra("idioma", curso.nombre)
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

