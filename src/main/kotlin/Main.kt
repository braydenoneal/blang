import parser.Program

fun main() {
    Thread.sleep(1_000)
    Program(
        """
        for i in range(10) {
            print(i)
        }
        """.trimIndent(),
    ).run()
}
