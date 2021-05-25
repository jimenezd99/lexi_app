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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import equipo.tres.lexi.ui.home.HomeActivity
import equipo.tres.lexi.ui.cursos.MisCursosActivity
import equipo.tres.lexi.R
import equipo.tres.lexi.ui.login.LoginActivity

class PerfilActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var storage:FirebaseFirestore
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
        storage= FirebaseFirestore.getInstance()
        val btnBack = findViewById(R.id.btn_back) as Button
        val tvLogout=findViewById<TextView>(R.id.tv_logout)
        val tvNombre=findViewById<TextView>(R.id.txt_nombre)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(nav)

        tvLogout.setOnClickListener {
            val logout=Firebase.auth.signOut()
            var intent: Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            Toast.makeText(baseContext, "${Firebase.auth.currentUser?.email.toString()} Se ha cerrado correctamente la sesión.", Toast.LENGTH_SHORT).show()
        }

        btnBack.setOnClickListener {
            super.onBackPressed()

        }
        cargarNombre(storage, tvNombre)

    }
    private fun cargarNombre(storage:FirebaseFirestore, tv_nombre:TextView){
        storage.collection("usuarios").whereEqualTo("email", auth.currentUser?.email)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Toast.makeText(this,"${document.id} => ${document.data}",Toast.LENGTH_SHORT)
                    val nombre:String=document.data.get("nombre").toString()
                    tv_nombre.setText(nombre)
//                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error getting documents.$exception",Toast.LENGTH_SHORT)
//                Log.w(TAG, "Error getting documents.", exception)
            }
//        val user=storage.collection("usuarios").whereArrayContains("email",auth.currentUser?.email.toString())
//        tv_nombre.setText(user.)

    }
}