package equipo.tres.lexi.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import equipo.tres.lexi.R

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val btn_back = findViewById(R.id.btn_back) as Button

        btn_back.setOnClickListener {
            super.onBackPressed()
        }
    }
}