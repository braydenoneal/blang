import program.Program

fun main() {
    // Wait a few seconds if testing the wait() function
    Thread.sleep(5_000)
    Program.initialize()
    Program(
        """
        b = 0
        
        fn plusB() {
            b += 1
            return b
        }
        
        print([plusB(), wait(25)])
        return
        
        print(1)
        wait(25)
        print(2)
        
        a = 0
        
        fn getA() {
            a += 1
            return a
        }
        
        fn sleep() {
            wait(25)
            return true
        }
        
        fn thing(x, y) {
            print(x)
            print(y)
        }
        
        thing(getA(), sleep())
    """.trimIndent(),
    ).run(true)
}
