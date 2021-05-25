package equipo.tres.lexi.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import equipo.tres.lexi.R
import equipo.tres.lexi.ui.ejercicios.TraducirActivity

class IntroductionActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val btn_back = findViewById(R.id.btn_back) as Button
        val tv_intro = findViewById(R.id.tv_intro) as TextView

        var intro = getIntent().getStringArrayListExtra("introduccion")
        var idioma = getIntent().getStringExtra("idioma")
        var leccion = getIntent().getStringExtra("nivel")
        var nombre = getIntent().getStringExtra("nombre")

        var texto: String = ""

        intro!!.forEach {
            texto += it + System.getProperty("line.separator")
        }

        tv_intro.text = texto



        btn_back.setOnClickListener {
            super.onBackPressed()
        }

        val btn_nivel= findViewById<Button>(R.id.btn_continuar)

        btn_nivel.setOnClickListener(){
            var intent = Intent(this, TraducirActivity::class.java)
            intent.putExtra("idioma", idioma)
            intent.putExtra("nombre", nombre)
            intent.putExtra("leccion", leccion)
            this!!.startActivity(intent)
        }

    }
}