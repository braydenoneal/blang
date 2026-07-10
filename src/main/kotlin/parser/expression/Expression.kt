package parser.expression

import parser.ParseException
import parser.Parser
import parser.Program
import parser.expression.operator.ArithmeticOperator
import parser.expression.operator.BooleanOperator
import parser.expression.operator.ComparisonOperator
import parser.expression.operator.UnaryOperator
import parser.expression.value.*
import tokenizer.Type
import java.util.*

interface Expression {
    fun evaluate(program: Program): Value<*>?

    interface Output

    data class Operand(val expression: Expression) : Output

    data class Operator(val operator: String) : Output

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

        fun parse(parser: Parser): Expression {
            val outputs: Deque<Output> = ArrayDeque()
            val operators: Stack<Operator> = Stack()
            var openedParenthesis = false
            var operand = true

            while (!(parser.peekIs(
                    Type.PARENTHESIS, ")",
                ) || parser.peekIs(
                    Type.SEMICOLON,
                ) || parser.peekIs(
                    Type.CURLY_BRACE,
                ) || parser.peekIs(
                    Type.SQUARE_BRACE, "]",
                ) || parser.peekIs(
                    Type.COMMA,
                ) || parser.peekIs(
                    Type.KEYWORD, "else",
                )) || openedParenthesis || operand
            ) {
                when (parser.peek().type) {
                    Type.BOOLEAN_OPERATOR, Type.COMPARISON_OPERATOR, Type.ARITHMETIC_OPERATOR -> {
                        operand = true
                        val operator = parser.next().value

                        if (!operators.empty() && operatorPrecedence[operator]!! <= operatorPrecedence[operators.peek().operator]!!) {
                            outputs.push(operators.pop())
                        }

                        operators.push(Operator(operator))
                    }

                    Type.PARENTHESIS -> if (parser.next().value == "(") {
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

                    else -> {
                        operand = false
                        val token = parser.peek()

                        if (parser.peekIs(Type.KEYWORD, "fn")) {
                            return FunctionValue.parse(parser)
                        }

                        var expression = when (token.type) {
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

                        val indices: MutableList<Expression> = ArrayList<Expression>()

                        while (parser.peekIs(Type.SQUARE_BRACE, "[")) {
                            parser.next()
                            indices.add(parse(parser))
                            parser.expect(Type.SQUARE_BRACE, "]")
                        }

                        if (!indices.isEmpty()) {
                            if (expression is VariableExpression) {
                                expression = NamedListAccessExpression(expression, indices)
                            } else if (expression is ListExpression) {
                                expression = ListAccessExpression(expression, indices)
                            }
                        }

                        while (parser.peekIs(Type.DOT)) {
                            parser.expect(Type.DOT)
                            val name = parser.expect(Type.IDENTIFIER)

                            expression = if (parser.peekIs(Type.PARENTHESIS, "(")) {
                                MemberCallExpression.parse(parser, expression, name)
                            } else {
                                MemberExpression(expression, name)
                            }
                        }

                        if (parser.peekIs(Type.KEYWORD, "if")) {
                            expression = IfElseExpression.parse(parser, expression)
                        } else if (parser.peekIs(Type.ASSIGN)) {
                            expression = AssignmentExpression.parse(parser, expression)
                        }

                        outputs.push(Operand(expression))
                    }
                }
            }

            while (!operators.empty()) {
                outputs.push(operators.pop())
            }

            val expressions = Stack<Expression>()

            while (!outputs.isEmpty()) {
                val output = outputs.removeLast()

                if (output is Operand) {
                    expressions.add(output.expression)
                } else if (output is Operator) {
                    val right = expressions.pop()
                    val left = expressions.pop()
                    val operator = output.operator

                    expressions.add(
                        when (operator) {
                            "and", "or" -> BooleanOperator(operator, left, right)
                            "==", "!=", "<=", ">=", "<", ">" -> ComparisonOperator(operator, left, right)
                            else -> ArithmeticOperator(operator, left, right)
                        },
                    )
                }
            }

            if (expressions.isEmpty()) {
                throw ParseException("Incomplete expression at " + parser.peek())
            }

            return expressions.peek()
        }
    }
}
