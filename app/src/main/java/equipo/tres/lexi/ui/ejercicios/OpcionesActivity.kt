package equipo.tres.lexi.ui.ejercicios

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import equipo.tres.lexi.R
import equipo.tres.lexi.ui.home.NivelesActivity

class OpcionesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opciones)

        val btn_back = findViewById(R.id.btn_back) as Button
        val btn_nivel= findViewById(R.id.btn_continuar) as Button

        btn_back.setOnClickListener {
            super.onBackPressed()
        }

        btn_nivel.setOnClickListener(){
            var intent = Intent(this, CompletarActivity::class.java)
            this!!.startActivity(intent)
        }
    }
}