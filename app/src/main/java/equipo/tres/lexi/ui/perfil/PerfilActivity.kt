package equipo.tres.lexi.ui.perfil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import equipo.tres.lexi.ui.home.HomeActivity
import equipo.tres.lexi.ui.cursos.MisCursosActivity
import equipo.tres.lexi.R
import equipo.tres.lexi.ui.login.LoginActivity

class PerfilActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val nav = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_inicio -> {
                val intent = Intent(this@PerfilActivity, HomeActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_cursos -> {
                val intent = Intent(this@PerfilActivity, MisCursosActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_perfil -> {
                val intent = Intent(this@PerfilActivity, PerfilActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
        }
        false

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        auth = Firebase.auth
        val btn_back = findViewById(R.id.btn_back) as Button
        val tv_logout=findViewById<TextView>(R.id.tv_logout)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(nav)

        tv_logout.setOnClickListener {
            val logout=Firebase.auth.signOut()
            var intent: Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            Toast.makeText(baseContext, "${Firebase.auth.currentUser?.email.toString()} Se ha cerrado correctamente la sesión.", Toast.LENGTH_SHORT).show()
        }

        btn_back.setOnClickListener {
            super.onBackPressed()

        }



    }
}