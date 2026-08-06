package testing.test

import program.expression.value.booleanvalue.BooleanValue
import program.expression.value.integer.IntegerValue
import program.expression.value.nullvalue.Null

class ControlStatements : Test() {
    override fun body(): String {
        return """
            list = []
            
            for i in 0..10 {
                if i == 5 { continue }
                list.append(i)
            }
            
            a = 5 in list
            
            b = 0
            
            for i in 0..10 {
                b = i
                if i == 5 { break }
            }
            
            fn emptyReturn() { return }
            
            c = emptyReturn()
        """.trimIndent()
    }

    override fun expects(): List<Expect> {
        return listOf(
            Expect("a", BooleanValue(false)),
            Expect("b", IntegerValue(5)),
            Expect("c", Null.VALUE),
        )
    }
}
