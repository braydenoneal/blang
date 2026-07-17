import parser.Parser
import program.Program

fun main() {
    Parser.initialize()
    Program(
        """
        a = [[0]]
        a[0][0] = 1
        print(a)
        """.trimIndent(),
    ).run()
}
