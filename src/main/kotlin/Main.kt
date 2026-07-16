import parser.Program
import parser.pratt.Parsers

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
