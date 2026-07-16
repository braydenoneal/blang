package testing.test

import program.expression.value.IntegerValue

class WhileLoops : Test() {
    override fun body(): String {
        return """
                i = 0
                
                while i < 10 {
                    i += 1
                }
                """.trimIndent()
    }

    override fun expects(): List<Expect> {
        return listOf(
            Expect("i", IntegerValue(10)),
        )
    }
}
