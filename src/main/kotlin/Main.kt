import program.Program

fun main() {
    Program.initialize()
    Program(
        """
            struct Test(a, b, c) {
                fn test() {
                    return self.b
                }
                
                static {
                    var TEST = 27
                    
                    fn test() {
                        return 1
                    }
                }
            }
            
            a = Test(1, 2, 3)
            print(a)
            print(a.c)
            print(a.test())
            print(Test.TEST)
            print(Test.test())
        """.trimIndent(),
    ).run(true)
}
