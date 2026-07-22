package program

import parser.Parser
import program.expression.BinaryOperators
import program.expression.UnaryOperators
import program.statement.FunctionStatement
import program.statement.ImportStatement
import program.statement.IncompleteException
import program.statement.StatementList

open class Program(
    open var source: String = "",
    open var parsed: Boolean = false,
    open var name: String = "name",
    open val imports: MutableList<ImportStatement> = mutableListOf(),
    open val statements: StatementList = StatementList(),
    open val functions: MutableMap<String, FunctionStatement> = mutableMapOf(),
    open val scopes: MutableList<Scope> = mutableListOf(),
) {
    var wait = false

    fun run(sleep: Boolean = false) {
        if (!parsed) {
            Parser(this)
        }

        while (true) {
            try {
                if (tick(sleep)) {
                    break
                }
            } catch (_: IncompleteException) {
                continue
            }
        }
    }

    fun parse() {
        Parser(this)
    }

    fun tick(sleep: Boolean = false): Boolean {
        wait = false

        while (true) {
            try {
                statements.runNext(this)
                return true
            } catch (_: IncompleteException) {
                if (wait) {
                    break
                }
            }
        }

        if (sleep) {
            Thread.sleep(1_000 / 5)
        }

        return false
    }

    fun waitUntilNextTick() {
        wait = true
    }

    fun addImport(importStatement: ImportStatement) {
        imports.add(importStatement)
    }

    fun addFunction(name: String, function: FunctionStatement) {
        functions[name] = function
    }

    fun getFunction(name: String): FunctionStatement? {
        return functions[name]
    }

    fun addScope(scope: Scope) {
        scopes.add(scope)
    }

    fun endScope() {
        scopes.removeLast()
    }

    open fun getCustomImportProgram(importStatement: ImportStatement): Program {
        return this
    }

    val scope: Scope get() = scopes.last()
    val topScope: Scope get() = scopes.first()

    companion object {
        fun initialize() {
            Parser.initialize()
            UnaryOperators.initialize()
            BinaryOperators.initialize()
        }
    }
}
