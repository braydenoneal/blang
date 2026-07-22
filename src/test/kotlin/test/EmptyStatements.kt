package testing.test

import program.expression.value.IntegerValue

class EmptyStatements : Test() {
    override fun body(): String {
        return """
            for i in range(end=3) {}
            
            a = 0
            
            while (a += 1) < 4 {}
        """.trimIndent()
    }

    override fun expects(): List<Expect> {
        return listOf(
            Expect("a", IntegerValue(4)),
        )
    }
}
