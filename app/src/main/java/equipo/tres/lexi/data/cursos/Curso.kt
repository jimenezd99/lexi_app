package equipo.tres.lexi.data.cursos

data class Curso (var nombre: String,
                  var image: Int,
                  var niveles: ArrayList<Nivel>,
                  var progreso: String ) {
}