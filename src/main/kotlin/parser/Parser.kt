package parser

import parser.expression.BuiltinExpressionParser
import parser.expression.ExpressionParser
import parser.statement.StatementParser
import parser.tokenizer.Token
import parser.tokenizer.Token.Companion.tokenize
import parser.tokenizer.Type
import program.Program
import program.Program.Companion.log
import program.Scope

open class Parser(val program: Program) {
    var tokens: MutableList<Token> = mutableListOf()
    var position = 0

    init {
        program.imports.clear()
        program.statements.clear()
        program.functions.clear()
        program.scopes.clear()

        try {
            tokens = tokenize("${program.source}\n")
        } catch (e: Exception) {
            log.error("Tokenize error", e)
        }

        program.scopes.add(Scope(null))

        try {
            if (tokens.isNotEmpty()) {
                while (position < tokens.size) {
                    program.statements.add(StatementParser.parse(this))
                }
            }
        } catch (e: Exception) {
            log.error("Parse error", e)
            program.statements.clear()
            program.functions.clear()
        }

        program.parsed = true
    }

    fun peek(): Token {
        var position = position

        while (tokens[position].type == Type.NEWLINE) {
            position++
        }

        return tokens[position]
    }

    fun peekAllowNewline(): Token {
        return tokens[position]
    }

    fun peek(skipNewline: Boolean): Token {
        return if (skipNewline) peek() else peekAllowNewline()
    }

    fun peekNullable(): Token? {
        if (position >= tokens.size) {
            return null
        }

        var position = position

        while (tokens[position].type == Type.NEWLINE) {
            position++

            if (position >= tokens.size) {
                return null
            }
        }

        return tokens[position]
    }

    fun peekIs(type: Type, value: String): Boolean {
        val token = peekNullable()
        return token != null && token.type == type && token.value == value
    }

    fun peekIs(type: Type): Boolean {
        val token = peekNullable()
        return token != null && token.type == type
    }

    fun peekIsAllowNewline(type: Type): Boolean {
        val token = peekAllowNewline()
        return token.type == type
    }

    fun next(): Token {
        while (tokens[position].type == Type.NEWLINE) {
            position++
        }

        return tokens[position++]
    }

    fun nextAllowNewline(): Token {
        return tokens[position++]
    }

    fun expect(type: Type, value: String) {
        val token = next()

        if (token.type == type && token.value == value) {
            return
        }

        throw ParseException("Expected token of type $type and value $value")
    }

    fun expect(type: Type): String {
        val token = next()

        if (token.type == type) {
            return token.value
        }

        throw ParseException("Expected token of type $type")
    }

    fun expectStatementEnd() {
        if (position >= tokens.size || peekIsAllowNewline(Type.RIGHT_CURLY_BRACE)) {
            return
        }

        val token = nextAllowNewline()

        if (token.type == Type.SEMICOLON || token.type == Type.NEWLINE) {
            return
        }

        throw ParseException("Expected semicolon or newline")
    }

    companion object {
        fun initialize() {
            ExpressionParser.initialize()
            StatementParser.initialize()
            BuiltinExpressionParser.initialize()
        }
    }
}
