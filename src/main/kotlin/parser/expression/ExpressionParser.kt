package parser.expression

import parser.ParseException
import parser.Parser
import parser.expression.Expression.*
import parser.expression.operator.ArithmeticOperator
import parser.expression.operator.BooleanOperator
import parser.expression.operator.ComparisonOperator
import parser.expression.operator.UnaryOperator
import parser.expression.value.*
import tokenizer.Type
import java.util.*

class ExpressionParser(val parser: Parser) {
    val outputs = ArrayDeque<Output>()
    val operators = Stack<Operator>()
    val expressions = Stack<Expression>()
    var openedParenthesis = false
    var operand = true

    fun parse(): Expression {
        while (expressionContinues() && parser.position < parser.tokens.size) {
            when (parser.peekAllowNewline().type) {
                Type.BOOLEAN_OPERATOR, Type.COMPARISON_OPERATOR, Type.ARITHMETIC_OPERATOR -> parseOperator()
                Type.PARENTHESIS -> parseParenthesis()
                Type.NEWLINE -> if (openedParenthesis) {
                    continue
                } else if (outputs.isEmpty()) {
                    parser.nextAllowNewline()
                } else {
                    break
                }

                else -> parseOperand()
            }
        }

        while (!operators.empty()) {
            outputs.push(operators.pop())
        }

        while (!outputs.isEmpty()) {
            addNextExpression()
        }

        if (expressions.isEmpty()) {
            throw ParseException("Incomplete expression at " + parser.peek())
        }

        return expressions.peek()
    }

    fun expressionContinues(): Boolean = !listOf(
        parser.peekIs(Type.COMMA),
        parser.peekIs(Type.SEMICOLON),
        parser.peekIs(Type.CURLY_BRACE),
        parser.peekIs(Type.PARENTHESIS, ")"),
        parser.peekIs(Type.SQUARE_BRACE, "]"),
        parser.peekIs(Type.KEYWORD, "else"),
    ).any { it } || openedParenthesis || operand

    fun parseOperator() {
        operand = true
        val operator = parser.next().value

        if (!operators.empty() && operatorPrecedence[operator]!! <= operatorPrecedence[operators.peek().operator]!!) {
            outputs.push(operators.pop())
        }

        operators.push(Operator(operator))
    }

    fun parseParenthesis() {
        if (parser.next().value == "(") {
            operand = true
            openedParenthesis = true
            operators.push(Operator("("))
        } else {
            openedParenthesis = false

            while (operators.peek().operator != "(") {
                outputs.push(operators.pop())
            }

            operators.pop()
        }
    }

    fun parseOperand() {
        operand = false

        var expression = parseValue()
        expression = parseListAccessor(expression)
        expression = parseMemberAccessor(expression)
        expression = parseInlineIfElse(expression)

        outputs.push(Operand(expression))
    }

    fun parseValue(): Expression {
        val token = parser.peek()

        return when (token.type) {
            Type.KEYWORD -> when (token.value) {
                "fn" -> FunctionValue.parse(parser)
                else -> throw ParseException("Unexpected keyword ${token.value} in expression")
            }

            Type.BOOLEAN -> BooleanValue(parser.next().value == "true")
            Type.QUOTE -> StringValue(parser.next().value)
            Type.FLOAT -> FloatValue(parser.next().value.toFloat())
            Type.INTEGER -> IntegerValue(parser.next().value.toInt())
            Type.SQUARE_BRACE -> ListExpression.parse(parser)
            Type.CURLY_BRACE -> StructExpression.parse(parser)
            Type.UNARY_OPERATOR -> UnaryOperator.parse(parser)
            Type.NULL -> Null.parse(parser)
            else -> VariableExpression.parse(parser)
        }
    }

    fun parseListAccessor(expression: Expression): Expression {
        var expression = expression
        val indices: MutableList<Expression> = ArrayList<Expression>()

        while (parser.peekIs(Type.SQUARE_BRACE, "[")) {
            parser.next()
            indices.add(Expression.parse(parser))
            parser.expect(Type.SQUARE_BRACE, "]")
        }

        if (!indices.isEmpty()) {
            if (expression is VariableExpression) {
                expression = NamedListAccessExpression(expression, indices)
            } else if (expression is ListExpression) {
                expression = ListAccessExpression(expression, indices)
            }
        }

        return expression
    }

    fun parseMemberAccessor(expression: Expression): Expression {
        var expression = expression

        while (parser.peekIs(Type.DOT)) {
            parser.expect(Type.DOT)
            val name = parser.expect(Type.IDENTIFIER)

            expression = if (parser.peekIs(Type.PARENTHESIS, "(")) {
                MemberCallExpression.parse(parser, expression, name)
            } else {
                MemberExpression(expression, name)
            }
        }

        return expression
    }

    fun parseInlineIfElse(expression: Expression): Expression {
        var expression = expression

        if (parser.peekIsAllowNewline(Type.KEYWORD, "if")) {
            expression = IfElseExpression.parse(parser, expression)
        } else if (parser.peekIs(Type.ASSIGN)) {
            expression = AssignmentExpression.parse(parser, expression)
        }

        return expression
    }

    fun addNextExpression() {
        val output = outputs.removeLast()

        if (output is Operand) {
            expressions.add(output.expression)
        } else if (output is Operator) {
            val right = expressions.pop()
            val left = expressions.pop()

            val operator = when (val operatorString = output.operator) {
                "and", "or" -> BooleanOperator(operatorString, left, right)
                "==", "!=", "<=", ">=", "<", ">" -> ComparisonOperator(operatorString, left, right)
                else -> ArithmeticOperator(operatorString, left, right)
            }

            expressions.add(operator)
        }
    }

    companion object {
        val operatorPrecedence: MutableMap<String, Int> = mutableMapOf(
            Pair("(", -3),
            Pair(")", -3),
            Pair("and", -2),
            Pair("or", -2),
            Pair("==", -1),
            Pair("!=", -1),
            Pair("<=", -1),
            Pair(">=", -1),
            Pair("<", -1),
            Pair(">", -1),
            Pair("+", 0),
            Pair("-", 0),
            Pair("*", 1),
            Pair("//", 1),
            Pair("/", 1),
            Pair("%", 1),
            Pair("^", 2),
        )
    }
}
