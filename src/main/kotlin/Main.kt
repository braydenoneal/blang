import parser.Parsers
import program.Program

fun main() {
    Parsers.initialize()
    Program(
        """
        for i in range(10) {
            print(i)
        }
        """.trimIndent(),
    ).run()
}
