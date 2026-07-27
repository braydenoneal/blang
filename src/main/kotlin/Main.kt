import program.Program

fun main() {
    Program.initialize()
    Program(
        """
            e = Range(0, 10, 2)
            
            for i in e {
                print(i)
            }
            
            print(Range.test())
            print(Range.TEST)
        """.trimIndent(),
    ).run(true)
}
