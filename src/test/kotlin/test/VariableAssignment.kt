package testing.test

import program.expression.value.IntegerValue

class VariableAssignment : Test() {
    override fun body(): String {
        return """
            a = 1
            a = 0
            b = 0
            b = a + 1
            c = b
            d = 0
            d += 1
            d -= 2
            `if` = 3
        """.trimIndent()
    }

    override fun expects(): List<Expect> {
        return listOf(
            Expect("a", IntegerValue(0)),
            Expect("b", IntegerValue(1)),
            Expect("c", IntegerValue(1)),
            Expect("d", IntegerValue(-1)),
            Expect("if", IntegerValue(3)),
        )
    }
}
