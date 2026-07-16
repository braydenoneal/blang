package program.expression.value

import parser.Parser
import program.expression.Expression

class Null {
    companion object {
        val VALUE = NullValue(Null())

        fun parse(parser: Parser): Expression {
            parser.next()
            return VALUE
        }
    }
}
