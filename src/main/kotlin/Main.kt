import parser.Parser
import program.Program

fun main() {
    Parser.initialize()
    Program(
        """
        s = { l: [0] }
        s.l.append(1)
        print(s)
        """.trimIndent(),
    ).run()
}
