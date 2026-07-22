import program.Program

fun main() {
    Program.initialize()
    Program(
        """
        fn test() {
            var a = 1
        }
        
        a = 0
        test()
        print(a)
    """.trimIndent(),
    ).run(true)
}
