package testing.test

import program.expression.value.IntegerValue

class Structs : Test() {
    override fun body(): String {
        return """
            struct Test(a, b, c) {
                fn test() {
                    return self.b
                }
                
                static {
                    val TEST = 27
                    
                    fn test2() {
                        return 1
                    }
                }
            }
            
            a = Test(1, 2, 3)
            b = a.c
            c = a.test()
            d = Test.TEST
            e = Test.test2()
            f = a.TEST
            g = a.test2()
        """.trimIndent()
    }

    override fun expects(): List<Expect> {
        return listOf(
            Expect("b", IntegerValue(3)),
            Expect("c", IntegerValue(2)),
            Expect("d", IntegerValue(27)),
            Expect("e", IntegerValue(1)),
            Expect("f", IntegerValue(27)),
            Expect("g", IntegerValue(1)),
        )
    }
}
