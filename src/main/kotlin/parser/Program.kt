package parser

import Logger
import parser.expression.Expression
import parser.expression.value.Value
import parser.statement.FunctionDeclaration
import parser.statement.ImportStatement
import parser.statement.Statement
import parser.statement.StatementList
import tokenizer.Token
import tokenizer.Token.Companion.tokenize
import tokenizer.Type

open class Program(
    open var source: String = "",
    open var name: String = "",
    open val imports: MutableList<ImportStatement> = mutableListOf(),
    open val statements: StatementList = StatementList(),
    open val functions: MutableMap<String, FunctionDeclaration> = mutableMapOf(),
    open val scopes: MutableList<Scope> = mutableListOf(),
) {
    var tokens: MutableList<Token> = mutableListOf()
    var parsed = false
    var position = 0
    var wait = false

    fun parse() {
        imports.clear()
        statements.clear()
        functions.clear()
        scopes.clear()

        try {
            tokens = tokenize(source)
        } catch (e: Exception) {
            log.error("Tokenize error", e)
        }

        scopes.add(Scope(null))

        try {
            if (!tokens.isEmpty()) {
                name = expect(Type.IDENTIFIER)
                expect(Type.SEMICOLON)

                while (position < tokens.size) {
                    statements.add(Statement.parse(this))
                }
            }
        } catch (e: Exception) {
            log.error("Parse error", e)
            statements.clear()
            functions.clear()
        }

        parsed = true
    }

    fun run() {
        if (!parsed) {
            parse()
        }

        try {
            while (true) {
                val result = tick(true)

                if (result != null) {
                    break
                }
            }
        } catch (e: Exception) {
            log.error("Run error", e)
        }
    }

    fun tick(sleep: Boolean = false): Statement? {
        wait = false

        var result = statements.runNext(this)

        while (true) {
            if (result != null) {
                return result
            }

            if (wait) {
                break
            }

            result = statements.runNext(this)
        }

        if (sleep) {
            Thread.sleep(1_000 / 5)
        }

        return result
    }

    fun waitUntilNextTick() {
        wait = true
    }

    fun addImport(importStatement: ImportStatement) {
        imports.add(importStatement)
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

    fun addFunction(name: String, function: FunctionDeclaration) {
        functions[name] = function
    }

    fun getFunction(name: String): FunctionDeclaration? {
        return functions[name]
    }

    fun newScope() {
        scopes.add(Scope(scopes.last()))
    }

    fun endScope() {
        scopes.removeLast()
    }

    open fun parseCustomBuiltins(name: String): Expression? {
        return null
    }

    open fun getCustomType(value: Value<*>): String? {
        return null
    }

    open fun getCustomImportProgram(importStatement: ImportStatement): Program {
        return this
    }

    val scope: Scope get() = scopes.last()
    val topScope: Scope get() = scopes.first()

    companion object {
        val log: Logger = Logger()
    }
}
