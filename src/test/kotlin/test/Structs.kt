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
                    var TEST = 27
                    
                    fn test() {
                        return 1
                    }
                }
            }
            
            a = Test(1, 2, 3)
            b = a.c
            c = a.test()
            d = Test.TEST
            e = Test.test()
        """.trimIndent()
    }

    override fun expects(): List<Expect> {
        return listOf(
            Expect("b", IntegerValue(3)),
            Expect("c", IntegerValue(2)),
            Expect("d", IntegerValue(27)),
            Expect("e", IntegerValue(1)),
        )
    }
}
