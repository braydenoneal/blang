package testing.test

import program.expression.value.IntegerValue
import program.expression.value.ListValue
import program.expression.value.PairValue
import program.expression.value.StringValue

class Maps : Test() {
    override fun body(): String {
        return """
            map = Map([
                "a" to 1,
                "b" to 2,
                "c" to 3,
            ])
            
            b = map["b"]
            keys = map.keys()
            values = map.values()
            entries = map.entries()
            
            total = 0
            
            for entry in map {
                total += entry.second
            }
        """.trimIndent()
    }

    override fun expects(): List<Expect> {
        return listOf(
            Expect("b", IntegerValue(2)),
            Expect(
                "keys",
                ListValue(
                    mutableListOf(
                        StringValue("a"),
                        StringValue("b"),
                        StringValue("c"),
                    ),
                ),
            ),
            Expect(
                "values",
                ListValue(
                    mutableListOf(
                        IntegerValue(1),
                        IntegerValue(2),
                        IntegerValue(3),
                    ),
                ),
            ),
            Expect(
                "entries",
                ListValue(
                    mutableListOf(
                        PairValue(StringValue("a") to IntegerValue(1)),
                        PairValue(StringValue("b") to IntegerValue(2)),
                        PairValue(StringValue("c") to IntegerValue(3)),
                    ),
                ),
            ),
            Expect("total", IntegerValue(6)),
        )
    }
}
