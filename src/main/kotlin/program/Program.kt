package program

import parser.Parser
import program.expression.BinaryOperators
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

    fun run() {
        if (!parsed) {
            Parser(this)
        }

        try {
            while (true) {
                try {
                    tick(true)
                    break
                } catch (_: IncompleteException) {
                    continue
                }
            }
        } catch (e: Exception) {
            log.error("Run error", e)
        }
    }

    fun parse() {
        Parser(this)
    }

    fun tick(sleep: Boolean = false) {
        wait = false

        while (true) {
            try {
                statements.runNext(this)
                return
            } catch (_: IncompleteException) {
                if (wait) {
                    break
                }
            }
        }

        if (sleep) {
            Thread.sleep(1_000 / 5)
        }
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

    fun newScope() {
        scopes.add(Scope(scopes.last()))
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
        val log: Logger = Logger()

        fun initialize() {
            Parser.initialize()
            BinaryOperators.initialize()
        }
    }
}
