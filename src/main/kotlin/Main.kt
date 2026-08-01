import program.Program

fun main() {
    Program.initialize()
    Program(
        """
            fruit = pair("Apple", 3)
            print(fruit.first)
            print(fruit.second)
            
            fruitMap = 
        """.trimIndent(),
    ).run(true)
}
