package parser

import Context
import Logger
import parser.statement.FunctionDeclaration
import parser.statement.ImportStatement
import parser.statement.Statement
import parser.statement.StatementList
import tokenizer.Token
import tokenizer.Token.Companion.tokenize
import tokenizer.Type
import java.util.*


class Program(source: String, private val context: Context) {
    private val imports: MutableList<ImportStatement> = ArrayList()
    private val statements: StatementList = StatementList()
    private val functions: MutableMap<String, FunctionDeclaration> = HashMap()
    private val topScope = Scope(null)
    private val scopes = Stack<Scope>()

    private var position = 0

    private val tokens: MutableList<Token>
    private val name: String

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

    fun context(): Context {
        return context
    }

    fun run() {
        try {
            var done = false

            while (!done) {
                done = statements.runNext(this) != null
            }
        } catch (e: Exception) {
            log.error("Run error", e)
        }
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

    val scope: Scope get() = scopes.peek()

    companion object {
        private val log: Logger = Logger()
    }
}
