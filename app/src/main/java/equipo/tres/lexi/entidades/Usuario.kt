package equipo.tres.lexi.entidades

import equipo.tres.lexi.Nivel
import java.io.Serializable

data class Usuario (var nombre: String,
                    var password: String,
                    var correo: String
                    // var cursos: ArrayList<> TODO: No se que clase de curso poner xD
                    ) : Serializable {
}