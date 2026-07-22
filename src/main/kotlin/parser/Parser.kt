package parser

import parser.expression.BuiltinExpressionParser
import parser.expression.ExpressionParser
import parser.statement.StatementParser
import parser.tokenizer.Token
import parser.tokenizer.Token.Companion.tokenize
import parser.tokenizer.TokenException
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
            tokens = tokenize(program.source)
        } catch (exception: TokenException) {
            log.error("Tokenize error", exception)
        }

        program.scopes.add(Scope(null))

        try {
            while (!peekIs(Type.END_OF_FILE)) {
                program.statements.add(StatementParser.parse(this))
            }
        } catch (exception: ParseException) {
            log.error("Parse error", exception)
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

    fun peekIs(type: Type): Boolean {
        return peek().type == type
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

    fun expect(type: Type): String {
        val token = next()

        if (token.type == type) {
            return token.value
        }

        throw ParseException("Expected token of type $type")
    }

    fun expectStatementEnd() {
        if (position >= tokens.size || peekAllowNewline().type == Type.RIGHT_CURLY_BRACE || peekAllowNewline().type == Type.END_OF_FILE) {
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
