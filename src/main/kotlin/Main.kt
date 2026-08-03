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
            print(a.c)
            print(a.test())
            print(Test.TEST)
            print(Test.test2())
            print(a.TEST)
            print(a.test2())
        """.trimIndent(),
    )

    try {
        program.run(true)
    } catch (exception: ProgramException) {
        print(exception.getError(program))
    }
}
