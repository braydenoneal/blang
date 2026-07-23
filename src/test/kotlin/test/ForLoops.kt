package testing.test

import program.expression.value.IntegerValue

class ForLoops : Test() {
    override fun body(): String {
        return """
            a = 0
            
            for i in [0, 1, 2] {
                a = i
            }
            
            b = 0
            
            for i in range(end=3) {
                b = i
            }
            
            c = 0
            
            for i in range(0, 3) {
                c = i
            }
            
            d = 0
            
            for i in range(0, 3, 1) {
                d = i
            }
            
            e = 0
            
            for _ in range(end=3) {
                e += 1
            }
        """.trimIndent()
    }

    override fun expects(): List<Expect> {
        return listOf(
            Expect("a", IntegerValue(2)),
            Expect("b", IntegerValue(2)),
            Expect("c", IntegerValue(2)),
            Expect("d", IntegerValue(2)),
            Expect("e", IntegerValue(3)),
        )
    }
}
