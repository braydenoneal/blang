package testing.test

import parser.expression.value.BooleanValue
import parser.expression.value.IntegerValue
import parser.expression.value.Null.Companion.VALUE

class ControlStatements : Test() {
    override fun body(): String {
        return """
                list = [];
                
                for i in range(10) {
                    if i == 5 { continue; }
                    list.append(i);
                }
                
                a = list.contains(5);
                
                b = 0;
                
                for i in range(10) {
                    b = i;
                    if i == 5 { break; }
                }
                
                fn emptyReturn() { return; }
                
                c = emptyReturn();
                """.trimIndent()
    }

    override fun expects(): List<Expect> {
        return listOf(
            Expect("a", BooleanValue(false)),
            Expect("b", IntegerValue(5)),
            Expect("c", VALUE)
        )
    }
}
