package parser.expression.infix

import parser.Parser
import parser.statement.ArgumentsParser
import parser.tokenizer.Token
import program.expression.CallExpression
import program.expression.Expression

class CallExpressionParser(override val precedence: Int) : InfixParser {
    override fun parse(parser: Parser, token: Token, left: Expression): Expression {
        val arguments = ArgumentsParser.parse(parser)
        return CallExpression(left, arguments)
    }
}
