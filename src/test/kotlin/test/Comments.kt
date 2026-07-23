package testing.test

import program.expression.value.IntegerValue

class Comments : Test() {
    override fun body(): String {
        return """
            # Comment !@#$/**&^#$%*/
            /* Another comment !@#$/**&^#$%*/
            /*
            Multiline !@#$/**&^#$%
            
            */
            
            a = 0
            
            /* Another comment !@#$/**&^#$%*/
            # Comment !@#$/**&^#$%*/
            /*
            Multiline !@#$/**&^#$%
            
            */
            
            b = 1
            
            /* Another comment !@#$/**&^#$%*/
            /*
            Multiline !@#$/**&^#$%
            
            */
            # Comment !@#$/**&^#$%*/
            
            c = 2
            
            /*
            Multiline !@#$/**&^#$%
            
            */
            # Comment !@#$/**&^#$%*/
            /* Another comment !@#$/**&^#$%*/
            
            d = 3
            
            /*
            Multiline !@#$/**&^#$%
            
            */
            /* Another comment !@#$/**&^#$%*/
            # Comment !@#$/**&^#$%*/
        """.trimIndent()
    }

    override fun expects(): List<Expect> {
        return listOf(
            Expect("a", IntegerValue(0)),
            Expect("b", IntegerValue(1)),
            Expect("c", IntegerValue(2)),
            Expect("d", IntegerValue(3)),
        )
    }
}
