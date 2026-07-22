package testing.test

import program.expression.value.IntegerValue

class BuiltinArguments : Test() {
    override fun body(): String {
        return """
            a = 0
            
            for i in range(8, 32, 2) {
                a += 1
            }
            
            b = 0
            
            for i in range(8, 32, step=2) {
                b += 1
            }
            
            c = 0
            
            for i in range(8, end=32, step=2) {
                c += 1
            }
            
            d = 0
            
            for i in range(start=8, end=32, step=2) {
                d += 1
            }
            
            e = 0
            
            for i in range(end=32 - 8, step=2) {
                e += 1
            }
            
            f = 0
            
            for i in range(end=(32 - 8) // 2) {
                f += 1
            }
        """.trimIndent()
    }

    override fun expects(): List<Expect> {
        return listOf(
            Expect("a", IntegerValue(12)),
            Expect("b", IntegerValue(12)),
            Expect("c", IntegerValue(12)),
            Expect("d", IntegerValue(12)),
            Expect("e", IntegerValue(12)),
            Expect("f", IntegerValue(12)),
        )
    }
}
