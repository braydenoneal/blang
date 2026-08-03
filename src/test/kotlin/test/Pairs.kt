package testing.test

import program.expression.value.IntegerValue
import program.expression.value.PairValue
import program.expression.value.StringValue

class Pairs : Test() {
    override fun body(): String {
        return """
            pair = "one" to 2
            first = pair.first
            second = pair.second
            pair2 = Pair("one", 2)
        """.trimIndent()
    }

    override fun expects(): List<Expect> {
        return listOf(
            Expect("first", StringValue("one")),
            Expect("second", IntegerValue(2)),
            Expect("pair2", PairValue(StringValue("one") to IntegerValue(2))),
        )
    }
}
