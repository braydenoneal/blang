package parser.expression.prefix

import parser.Parser
import parser.tokenizer.Token
import program.expression.Expression
import program.expression.value.CallableValue

class FunctionValueParser : PrefixParser {
    override fun parse(parser: Parser, token: Token): Expression {
        return CallableValue(FunctionParser.parse(parser, false))
    }
}
