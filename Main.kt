package Programacion01.Ahorcado
//import Programacion01.Ahorcado.ReproductorMidi
//import Programacion01.Ahorcado.DibujoAhorcado

fun main() {

    // Crear instancia del reproductor MIDI
    val reproductor = ReproductorMidi("pugnodollari.mid") //src/Programacion01/Ahorcado/pugnodollari.mid
    println("Cargando el Juego")
    Thread.sleep(2000)

    // Juego
    val palabras = listOf("manzana", "banana", "cereza")
    val palabraSecreta = palabras.random() //solucion
    var fallos = 0
    val maxFallos = 7
    val letrasAdivinadas = mutableSetOf<Char>() //estado de la respuesta

    while (fallos < maxFallos) {
        println("Adivina la palabra: " + palabraSecreta.map { if (letrasAdivinadas.contains(it)) it else '_' }.joinToString(" "))

        print("Introduce una letra: ")
        val entrada = readLine()

        if (entrada != null && entrada.isNotEmpty()) {
            val letra = entrada[0]

            if (palabraSecreta.contains(letra)) {
                letrasAdivinadas.add(letra)

                if (palabraSecreta.all { letrasAdivinadas.contains(it) }) {
                    println("¡Felicidades, adivinaste la palabra!")
                    break
                }

            } else {
                fallos++
                DibujoAhorcado.dibujar(fallos)

                if (fallos == maxFallos) {
                    println("¡Has perdido! La palabra era $palabraSecreta")
                }
            }
        }
    }
    // Cerrar el reproductor MIDI
    reproductor.cerrar()
}
/*

 */