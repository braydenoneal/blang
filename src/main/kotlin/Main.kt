import program.Program

fun main() {
    Program.initialize()
    Program(
        """
            fn test(a = 0, b, c = 1) {
                print([a, b, c])
            }
            
            test(1, 2, 3)
            test(1, 2)
            test(b=2)
        """.trimIndent(),
    ).run(true)
}
