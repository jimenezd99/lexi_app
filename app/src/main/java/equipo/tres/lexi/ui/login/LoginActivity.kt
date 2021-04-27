package equipo.tres.lexi.ui.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.BaseColumns
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import equipo.tres.lexi.*
import equipo.tres.lexi.conexion.ConexionSQLiteHelper
import equipo.tres.lexi.entidades.Usuario
import equipo.tres.lexi.utilidadesSQLite.Utilidades

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val btn_crear_cuenta = findViewById<Button>(R.id.crear_cuenta)
        val btn_trampa= findViewById<TextView>(R.id.recuperar_contrasenia)
        val btnLogin= findViewById<Button>(R.id.login)

        btn_crear_cuenta.setOnClickListener {
            var intento: Intent = Intent(this, RegistroActivity::class.java)
            startActivity(intento)
        }

        btn_trampa.setOnClickListener(){
            var intent: Intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        // Lee la BD en busca del usuario.
        btnLogin.setOnClickListener {

            val txtUserName = findViewById<EditText>(R.id.username)
            val txtPassword = findViewById<EditText>(R.id.password)

            val conn = ConexionSQLiteHelper(this)

            val db = conn.readableDatabase

            val projection = arrayOf(BaseColumns._ID,
                Utilidades.TablaUsuario.COLUMN_NAME_NOMBRE,
                Utilidades.TablaUsuario.COLUMN_NAME_PASSWORD,
                Utilidades.TablaUsuario.COLUMN_NAME_CORREO)

            val selection = "(${Utilidades.TablaUsuario.COLUMN_NAME_CORREO}, ${Utilidades.TablaUsuario.COLUMN_NAME_PASSWORD}) = (?, ?)"
            val selectionArgs = arrayOf(txtUserName.text.toString(), txtPassword.text.toString())

            val cursor = db.query(
                Utilidades.TablaUsuario.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
            )

            var usuario : Usuario? = null

            with(cursor) {
                while (moveToNext()) {
                    usuario = Usuario(getString(1), getString(2), getString(3))
                }
            }

            if (usuario != null) {
                Toast.makeText(this, "Bienvenido ${usuario!!.nombre}", Toast.LENGTH_LONG).show()

                var intent: Intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("usuario", usuario)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_LONG).show()
            }

        }

        // Este codigo ya estaba generado
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
                .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
            var intent: Intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                        username.text.toString(),
                        password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                                username.text.toString(),
                                password.text.toString()
                        )
                }
                false
            }

            /*login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString())
            }*/
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
                applicationContext,
                "$welcome $displayName",
                Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}