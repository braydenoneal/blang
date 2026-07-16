package testing.test

import program.expression.value.FloatValue
import program.expression.value.IntegerValue
import program.expression.value.StringValue

class MathFunctions : Test() {
    override fun body(): String {
        return """
                abs = abs(-1)
                int = int(2.0)
                float = float(3)
                str = str(4)
                round = round(5.4)
                min = min(6, 7)
                max = max(6, 7)
                """.trimIndent()
    }

    override fun expects(): List<Expect> {
        return listOf(
            Expect("abs", IntegerValue(1)),
            Expect("int", IntegerValue(2)),
            Expect("float", FloatValue(3.0f)),
            Expect("str", StringValue("4")),
            Expect("round", IntegerValue(5)),
            Expect("min", IntegerValue(6)),
            Expect("max", IntegerValue(7)),
        )
    }
}
