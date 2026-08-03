package testing.test

import program.expression.value.*

class Maps : Test() {
    override fun body(): String {
        return """
            map = {
                "a": 1,
                "b": 2,
                "c": 3,
            }
            
            b = map["b"]
            keys = map.keys()
            values = map.values()
            entries = map.entries()
            
            total = 0
            
            for entry in map {
                total += entry.second
            }
            
            map2 = Map([
                "a" to 1,
                "b" to 2,
                "c" to 3,
            ])
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
            Expect(
                "map2",
                MapValue(
                    mutableMapOf(
                        StringValue("a") to IntegerValue(1),
                        StringValue("b") to IntegerValue(2),
                        StringValue("c") to IntegerValue(3),
                    ),
                ),
            ),
        )
    }
}
