import parser.Parsers
import program.Program

fun main() {
    Parsers.initialize()
    Program(
        """
        print("Hello, world!")
        """.trimIndent(),
    ).run()
}
