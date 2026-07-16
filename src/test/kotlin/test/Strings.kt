package testing.test

import program.expression.value.StringValue

class Strings : Test() {
    override fun body(): String {
        return """
                a = "a"
                b = 'b'
                c = a + b
                d = "1" + 6
                """.trimIndent()
    }

    override fun expects(): List<Expect> {
        return listOf(
            Expect("a", StringValue("a")),
            Expect("b", StringValue("b")),
            Expect("c", StringValue("ab")),
            Expect("d", StringValue("16")),
        )
    }
}
