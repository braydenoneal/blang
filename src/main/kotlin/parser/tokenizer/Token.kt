package parser.tokenizer

import java.util.regex.Pattern

class Token(val value: String, val type: Type, val span: Span) {
    companion object {
        val quote_replace = mutableMapOf(
            "\\t" to "\t",
            "\\b" to "\b",
            "\\n" to "\n",
            "\\r" to "\r",
            "\\'" to "\'",
            "\\\"" to "\"",
            "\\\\" to "\\",
        )

        fun tokenize(source: String): MutableList<Token> {
            val tokens: MutableList<Token> = mutableListOf()
            var position = 0
            var span = Span.NONE

            while (position < source.length) {
                var error = true

                for (type in Type.entries) {
                    val matcher = Pattern.compile("^${type.regex}").matcher(source.substring(position))

                    if (matcher.find()) {
                        val group = if (type == Type.QUOTE) matcher.group(0) else matcher.group(1)
                        span = Span(position, position + group.length)

                        if (type == Type.QUOTE) {
                            var string = group.substring(1, group.length - 1)
                            string = quote_replace.entries.fold(string) { string, (key, value) -> string.replace(key, value) }
                            tokens.add(Token(string, type, span))
                        } else if (type == Type.IDENTIFIER && group.startsWith("`")) {
                            tokens.add(Token(group.substring(1, group.length - 1), type, span))
                        } else if (type != Type.WHITESPACE && type != Type.COMMENT) {
                            tokens.add(Token(group, type, span))
                        }

                        position += group.length
                        error = false
                        break
                    }
                }

                if (error) {
                    throw TokenException("Unrecognized character '${source[position]}' at position $position", span)
                }
            }

            tokens.add(Token("", Type.END_OF_FILE, Span(source.length, source.length)))
            return tokens
        }
    }
}
