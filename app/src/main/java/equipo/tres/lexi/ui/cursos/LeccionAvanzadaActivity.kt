package equipo.tres.lexi.ui.cursos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import equipo.tres.lexi.ui.home.NivelesActivity
import equipo.tres.lexi.R

class LeccionAvanzadaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leccion_avanzada)
        val btn_back = findViewById(R.id.btn_back) as Button
        val btn_continuar = findViewById(R.id.btn_continuar) as Button

        btn_continuar.setOnClickListener {
            val intent = Intent(this, NivelesActivity::class.java)
            startActivity(intent)
            finish()
        }
        btn_back.setOnClickListener {
            super.onBackPressed()
            finish()

        }
    }
}