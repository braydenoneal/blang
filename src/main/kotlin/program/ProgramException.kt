package program

import parser.tokenizer.Span

open class ProgramException(message: String, val span: Span = Span.NONE) : RuntimeException(message) {
    fun getError(program: Program, isRed: Boolean = true): String {
        val string = StringBuilder()

        if (isRed) {
            string.append("\u001B[31m")
        }

        string.appendLine(message)

        val start = span.start
        val end = span.end
        var position = 0

        for (line in program.source.lines()) {
            if (start >= position && start < position + line.length) {
                string.appendLine(line)

                val lineStart = start - position

                string.append(" ".repeat(lineStart) + "^".repeat(end - start))
                break
            }

            position += line.length + 1
        }

        if (isRed) {
            string.append("\u001B[0m")
        }

        return string.toString()
    }
}
