package testing.test

import program.expression.value.IntegerValue

class Scope : Test() {
    override fun body(): String {
        return """
            fn x(a) {
                return a + 1
            }
            
            fn y(a) {
                return a + 2
            }
            
            fn z() {
                b = 1
                return b + x(b) + y(b)
            }
            
            a = 0
            a += z()
            
            fn test(c, d) {
                return d
            }
            
            c = 0
            
            d = test(1, c)
            
            fn assign() {
                var e = 1
            }
            
            e = 0
            assign()
        """.trimIndent()
    }

    override fun expects(): List<Expect> {
        return listOf(
            Expect("a", IntegerValue(6)),
            Expect("d", IntegerValue(0)),
            Expect("e", IntegerValue(0)),
        )
    }
}
