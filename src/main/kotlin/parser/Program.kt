package parser

import Logger
import parser.expression.Expression
import parser.statement.FunctionDeclaration
import parser.statement.ImportStatement
import parser.statement.Statement
import parser.statement.StatementList
import tokenizer.Token
import tokenizer.Token.Companion.tokenize
import tokenizer.Type
import java.util.*

open class Program(source: String) {
    val imports: MutableList<ImportStatement> = ArrayList()
    val statements: StatementList = StatementList()
    val functions: MutableMap<String, FunctionDeclaration> = HashMap()
    val topScope = Scope(null)
    val scopes = Stack<Scope>()

    var position = 0

    val tokens: MutableList<Token>
    val name: String
    var wait = false

    init {
        var tokens: MutableList<Token>

        try {
            tokens = tokenize(source)
        } catch (e: Exception) {
            log.error("Tokenize error", e)
            tokens = ArrayList()
        }

        this.tokens = tokens
        scopes.push(topScope)
        var name = ""

        try {
            if (!tokens.isEmpty()) {
                name = expect(Type.IDENTIFIER)
                expect(Type.SEMICOLON)
                parse()
            }
        } catch (e: Exception) {
            log.error("Parse error", e)
            statements.clear()
            functions.clear()
        }

        this.name = name
    }

    fun run() {
        try {
            while (true) {
                val result = tick()

                if (result != null) {
                    break
                }
            }
        } catch (e: Exception) {
            log.error("Run error", e)
        }
    }

    fun tick(): Statement? {
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

//        Thread.sleep(1_000 / 5)

        return result
    }

    fun waitUntilNextTick() {
        wait = true
    }

    fun name(): String {
        return name
    }

    fun imports(): MutableList<ImportStatement> {
        return imports
    }

    fun addImport(importStatement: ImportStatement) {
        imports.add(importStatement)
    }

    fun parse() {
        while (position < tokens.size) {
            statements.add(Statement.parse(this))
        }
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
        scopes.add(Scope(scopes.peek()))
    }

    fun endScope() {
        scopes.pop()
    }

    fun topScope(): Scope {
        return topScope
    }

    open fun parseCustomBuiltins(name: String): Expression? {
        return null
    }

    val scope: Scope get() = scopes.peek()

    companion object {
        val log: Logger = Logger()
    }
}
