import program.Program

fun main() {
    val a = 5 + 5.0
    println(a)
    Program.initialize()
    Program(
        """
            print(2)
            a = print
            a(1)
        """.trimIndent(),
    ).run(true)
}
