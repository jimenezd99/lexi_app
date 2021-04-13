package equipo.tres.lexi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class NivelesActivity : AppCompatActivity() {

    var adapter: NivelAdapter? = null
    var niveles = ArrayList<Nivel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val btn_back = findViewById(R.id.btn_back) as Button

        btn_back.setOnClickListener {
            super.onBackPressed()
        }
        //homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val gvNiveles: GridView = findViewById(R.id.gv_niveles)

        cargarNiveles()
        adapter = NivelAdapter(this, niveles)
        gvNiveles.adapter = adapter
    }
    private fun cargarNiveles(){
        
    }

    class NivelAdapter : BaseAdapter {
        var niveles = ArrayList<Nivel>()
        var contexto: Context? = null

        constructor(context: Context, niveles: ArrayList<Nivel>) {
            this.contexto = context
            this.niveles = niveles
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var nivel = niveles[position]
            var inflator =
                contexto!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var vista = inflator.inflate(R.layout.cursos, null)

            var imagen = vista.findViewById(R.id.iv_imagenNivel) as ImageView
            var nombre = vista.findViewById(R.id.tv_nivel) as TextView

            imagen.setImageResource(nivel.image)
            nombre.setText(nivel.nombre)


            imagen.setOnClickListener() {
                var intent = Intent(contexto, IntroductionActivity::class.java)
                intent.putExtra("nombre", nivel.nombre)
                intent.putExtra("imagen", nivel.image)
                intent.putExtra("introduccion", (nivel.introduccion))
                intent.putExtra("nivel", nivel.nivel)
                intent.putExtra("progreso", nivel.progreso)
                contexto!!.startActivity(intent)
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