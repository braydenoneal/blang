import parser.Parser
import program.Program

fun main() {
    Parser.initialize()
    Program(
        """
        print("Hello, world!")
        """.trimIndent(),
    ).run()
}
