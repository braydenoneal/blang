package testing.test

import program.expression.value.string.StringValue

class TypeFunction : Test() {
    override fun body(): String {
        return """
            boolean = type(false)
            float = type(0.1)
            function = type(fn a: a)
            integer = type(1)
            list = type([0])
            nullType = type(null)
            range = type(0..1)
            string = type("string")
        """.trimIndent()
    }

    override fun expects(): List<Expect> {
        return listOf(
            Expect("boolean", StringValue("Boolean")),
            Expect("float", StringValue("Float")),
            Expect("function", StringValue("Function")),
            Expect("integer", StringValue("Integer")),
            Expect("list", StringValue("List")),
            Expect("nullType", StringValue("Null")),
            Expect("range", StringValue("Range")),
            Expect("string", StringValue("String")),
        )
    }
}
