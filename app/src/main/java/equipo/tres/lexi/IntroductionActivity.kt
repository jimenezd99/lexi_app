package equipo.tres.lexi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity

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
            var intent = Intent(this, LeccionAvanzadaActivity::class.java)
            this!!.startActivity(intent)
        }

    }
}