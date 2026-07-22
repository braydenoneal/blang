import program.Program

fun main() {
    Program.initialize()
    Program(
        """
        fn add(a, b) {
            return b
        }
        
        a = 0
        
        print(add(wait(20), a += 1))
    """.trimIndent(),
    ).run(true)
}
