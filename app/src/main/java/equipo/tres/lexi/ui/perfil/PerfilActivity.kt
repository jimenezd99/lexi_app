package equipo.tres.lexi.ui.perfil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
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
    private lateinit var usuario: FirebaseAuth
    private lateinit var storage: FirebaseFirestore
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
        usuario = Firebase.auth
        storage = FirebaseFirestore.getInstance()
        val btnBack = findViewById(R.id.btn_back) as Button
        val tvLogout = findViewById<TextView>(R.id.tv_logout)
        val tvNombre = findViewById<TextView>(R.id.txt_nombre)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val tvProgreso = findViewById(R.id.tv_progreso) as TextView
        val barraProgreso1 = findViewById(R.id.progress_bar_ingles) as ProgressBar
        val tv_idioma1 = findViewById(R.id.tv_idioma1) as TextView
//        val barraProgreso2= findViewById(R.id.progress_bar_frances) as ProgressBar
        navView.setOnNavigationItemSelectedListener(nav)

        tvLogout.setOnClickListener {
            val logout = Firebase.auth.signOut()
            var intent: Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnBack.setOnClickListener {
            super.onBackPressed()
            //que mejor vaya al inicio... porque el botón para atrás causa la aglomeración de pantallas
            finish()

        }
        cargarDatos(storage, tvNombre, tvProgreso, barraProgreso1, tv_idioma1)

    }

    private fun cargarDatos(
        storage: FirebaseFirestore,
        tv_nombre: TextView,
        tvProgreso: TextView,
        barraProgreso: ProgressBar,
        tv_idioma1: TextView
    ) {
        storage.collection("progreso").whereEqualTo("usuario", usuario.currentUser!!.email)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    val progreso: String = document.data.get("progreso").toString()
                    val idioma: String = document.data.get("idioma").toString()
                    val valor = progreso.split(".")[0]
                    tvProgreso.setText("${idioma.toString()}  ${progreso.toString()} %")
                    tv_idioma1.setText("${idioma.toString()}  ${progreso.toString()} %")
                    barraProgreso.setProgress(valor.toInt())

                }
                cargarNombre(storage,tv_nombre)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error getting documents.$exception", Toast.LENGTH_SHORT)
            }

    }

    private fun cargarNombre(storage:FirebaseFirestore, tv_nombre:TextView){
        storage.collection("usuarios").whereEqualTo("email", usuario.currentUser?.email)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Toast.makeText(this,"${document.id} => ${document.data}",Toast.LENGTH_SHORT)
                    val nombre:String=document.data.get("nombre").toString()
                    tv_nombre.setText(nombre)
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error getting documents.$exception",Toast.LENGTH_SHORT)
            }

    }
}