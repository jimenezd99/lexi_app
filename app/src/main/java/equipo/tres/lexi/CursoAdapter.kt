package equipo.tres.lexi

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView


class CursoAdapter: BaseAdapter {

    var cursos = ArrayList<MiCurso>()
    var context: Context? = null

    constructor(context: Context, miCursos: ArrayList<MiCurso>){
        this.context = context
        this.cursos = miCursos
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var curso = cursos[position]
        var inflator = LayoutInflater.from(context)
        var vista = inflator.inflate(R.layout.curso_view, null)

        var id_curso: ImageView = vista.findViewById(R.id.id_curso)
        var nivel_curso: TextView = vista.findViewById(R.id.nivel_curso)
        var idioma_curso: TextView = vista.findViewById(R.id.idioma_curso)
        var progreso_curso: TextView = vista.findViewById(R.id.progreso_curso)
        var btn_continuar: Button = vista.findViewById(R.id.btn_continuar)

        id_curso.setImageResource(curso.bandera)
        nivel_curso.setText(curso.nivel)
        idioma_curso.setText(curso.idioma)
        progreso_curso.setText(curso.progreso)


        btn_continuar.setOnClickListener {
            var intent = Intent(context, LeccionAvanzadaActivity::class.java)
            context!!.startActivity(intent)
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