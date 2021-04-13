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
import equipo.tres.lexi.ui.Tema

class TemaAdapter: BaseAdapter {

    var temas = ArrayList<Tema>()
    var context: Context? = null

    constructor(context: Context, temas: ArrayList<Tema>){
        this.context = context
        this.temas = temas
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var tema = temas[position]
        var inflator = LayoutInflater.from(context)
        var vista = inflator.inflate(R.layout.tema_view, null)

        var image: ImageView = vista.findViewById(R.id.tema_img)
        var titulo_tema: TextView = vista.findViewById(R.id.tv_tema_text)

        image.setImageResource(tema.image)
        image.setBackgroundResource(tema.fondo)
        titulo_tema.setText(tema.tema)


        image.setOnClickListener {
            var intent = Intent(context, LeccionAvanzadaActivity::class.java)
            context!!.startActivity(intent)
        }

        return vista
    }

    override fun getItem(position: Int): Any {
        return temas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return temas.size
    }
}