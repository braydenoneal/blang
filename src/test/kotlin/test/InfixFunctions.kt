package testing.test

import program.expression.value.integer.IntegerValue

class InfixFunctions : Test() {
    override fun body(): String {
        return """
            a = 0
            
            for _ in 0..10 step 2 {
                a += 1
            }
        """.trimIndent()
    }

    override fun expects(): List<Expect> {
        return listOf(
            Expect("a", IntegerValue(5)),
        )
    }
}
