import parser.Parser
import program.Program

fun main() {
    Parser.initialize()
    Program(
        """
        a = 0
        print(a)
        """.trimIndent(),
    ).run()
}
