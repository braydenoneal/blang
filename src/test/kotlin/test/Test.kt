package testing.test

import program.Program
import program.ProgramException
import program.expression.value.Value

abstract class Test {
    private val program = Program(body())

    abstract fun body(): String

    abstract fun expects(): List<Expect>

    fun run(): Result {
        val expects = expects()
        val failed = mutableListOf<String>()

        try {
            program.run()
        } catch (exception: ProgramException) {
            failed.add(exception.getError(program))
        }

        for (expect in expects) {
            try {
                val value = program.topScope.get(expect.name)

                if (expect.value != value) {
                    failed.add("\u001B[31mFailed: ${expect.name} is $value, expected ${expect.value}\u001B[0m")
                }
            } catch (exception: ProgramException) {
                failed.add(exception.getError(program))
            }
        }

        if (failed.isNotEmpty()) {
            println("\u001B[31m${javaClass.getSimpleName()}\u001B[0m")

            for (failure in failed) {
                println(failure)
            }
        }

        if (expects().isEmpty()) {
            return Result(1 - failed.size, 1)
        }

        return Result(expects.size - failed.size, expects.size)
    }

    class Expect(val name: String, val value: Value<*>)

    class Result(val passed: Int, val total: Int)
}
