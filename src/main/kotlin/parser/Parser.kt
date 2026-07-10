package parser

import parser.Program.Companion.log
import parser.statement.Statement
import tokenizer.Token
import tokenizer.Token.Companion.tokenize
import tokenizer.Type

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
        } catch (e: Exception) {
            log.error("Tokenize error", e)
        }

        program.scopes.add(Scope(null))

        try {
            if (!tokens.isEmpty()) {
                program.name = expect(Type.IDENTIFIER)
                expect(Type.SEMICOLON)

                while (position < tokens.size) {
                    program.statements.add(Statement.parse(this))
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
        return tokens[position]
    }

    fun peekIs(type: Type, value: String): Boolean {
        val token = peek()
        return token.type == type && token.value == value
    }

    fun peekIs(type: Type): Boolean {
        val token = peek()
        return token.type == type
    }

    fun next(): Token {
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
}
