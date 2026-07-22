package testing.test

import program.expression.value.IntegerValue

class Wait : Test() {
    override fun body(): String {
        return """
            fn test(a, b, c) {}
            
            a = 0
            
            test(wait(20), a += 1, wait(20))
            
            b = 0
            
            for i in [0, 1] {
                wait(20)
                b += 1
                wait(20)
            }
            
            c = 0
            
            while (c += 1) < 3 {
                wait(20)
            }
        """.trimIndent()
    }

    override fun expects(): List<Expect> {
        return listOf(
            Expect("a", IntegerValue(1)),
            Expect("b", IntegerValue(2)),
            Expect("c", IntegerValue(3)),
        )
    }
}
