package equipo.tres.lexi.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import equipo.tres.lexi.ui.cursos.LeccionAvanzadaActivity
import equipo.tres.lexi.R
import equipo.tres.lexi.ui.ejercicios.TraducirActivity

class IntroductionActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val btn_back = findViewById(R.id.btn_back) as Button

        btn_back.setOnClickListener {
            super.onBackPressed()
        }
        val btn_nivel= findViewById(R.id.btn_continuar) as Button
        btn_nivel.setOnClickListener(){
            var intent = Intent(this, TraducirActivity::class.java)
            this!!.startActivity(intent)
        }

    }
}