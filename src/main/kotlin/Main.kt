import program.Program

fun main() {
    Program.initialize()
    Program(
        """
            fruit = pair("Apple", 3)
            print(fruit.first)
            print(fruit.second)
            
            foo = [pair("Spiderman 1", 2002), pair("Spiderman 2", 2004)]
            
            spidermanMovies = hash(foo)
            print(spidermanMovies.keys())
            print(spidermanMovies["Spiderman 1"])
        """.trimIndent(),
    ).run(true)
}
