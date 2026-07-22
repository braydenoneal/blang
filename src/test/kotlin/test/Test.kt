package testing.test

import program.Program
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

            for (expect in expects) {
                val value = program.topScope.get(expect.name)

                if (expect.value != value) {
                    failed.add("\u001B[31mFailed: ${expect.name} is $value, expected ${expect.value}\u001B[0m")
                }
            }
        } catch (exception: Exception) {
            failed.add("\u001B[31m${exception}\u001B[0m")
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

    data class Expect(val name: String, val value: Value<*>)

    data class Result(val passed: Int, val total: Int)
}
