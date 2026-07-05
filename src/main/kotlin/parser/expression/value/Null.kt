package parser.expression.value

import parser.Program
import parser.expression.Expression

class Null {
    companion object {
        val VALUE = NullValue(Null())

        fun parse(program: Program): Expression {
            program.next()
            return VALUE
        }
    }
}
