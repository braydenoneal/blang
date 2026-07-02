import parser.Program

fun main() {
    Thread.sleep(1_000)
    Program(
        """
        fileName;
        
        for i in range(10) {
            print(i);
            wait(5);
        }
        """.trimIndent(),
        Context(0, 0),
    ).run()
}
