import program.Program
import program.ProgramException

fun main() {
    Program.initialize()
    val program = Program(
        """
            struct Test(a, b, c) {
                fn test() {
                    return self.b
                }
                
                static {
                    val TEST = 27
                    
                    fn test2() {
                        return 1
                    }
                }
            }
            
            a = Test(1, 2, 3)
            b = a.c
            c = a.test()
            d = Test.TEST
            e = Test.test2()
            f = a.TEST
            g = a.test2()
        """.trimIndent(),
    )

    try {
        program.run(true)
    } catch (exception: ProgramException) {
        print(exception.getError(program))
    }
}
