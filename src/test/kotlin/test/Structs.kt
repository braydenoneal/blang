package testing.test

import program.expression.value.IntegerValue
import program.expression.value.ListValue
import program.expression.value.StringValue
import program.expression.value.StructValue

class Structs : Test() {
    override fun body(): String {
        return """
                a = { a: 1 }
                b = a.a
                c = { a: { a: 1 }}
                d = { a: 1 }
                d.remove("a")
                e = c.keys()
                f = c.values()
                g = c.entries()
                h = { a: { a: 0 }}
                h.a.a = 1
                """.trimIndent()
    }

    override fun expects(): List<Expect> {
        return listOf(
            Expect("a", StructValue(mutableListOf(Pair("a", IntegerValue(1))))),
            Expect("b", IntegerValue(1)),
            Expect("c", StructValue(mutableListOf(Pair("a", StructValue(mutableListOf(Pair("a", IntegerValue(1)))))))),
            Expect("d", StructValue(mutableListOf())),
            Expect("e", ListValue(mutableListOf(StringValue("a")))),
            Expect("f", ListValue(mutableListOf(StructValue(mutableListOf(Pair("a", IntegerValue(1))))))),
            Expect("g", ListValue(mutableListOf(StructValue(mutableListOf(Pair("key", StringValue("a")), Pair("value", StructValue(mutableListOf(Pair("a", IntegerValue(1)))))))))),
            Expect("h", StructValue(mutableListOf(Pair("a", StructValue(mutableListOf(Pair("a", IntegerValue(1)))))))),
        )
    }
}
