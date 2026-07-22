package parser.expression.prefix

import parser.Parser
import parser.tokenizer.Token
import program.expression.Expression
import program.expression.value.FunctionValue

class FunctionExpressionParser : PrefixParser {
    override fun parse(parser: Parser, token: Token): Expression {
        return FunctionValue(FunctionParser.parse(parser, false))
    }
}
