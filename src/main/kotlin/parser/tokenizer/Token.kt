package parser.tokenizer

class Token(val value: String, val type: Type, val span: Span) {
    companion object {
        val quote_replace = mutableMapOf(
            """\t""" to "\t",
            """\b""" to "\b",
            """\n""" to "\n",
            """\r""" to "\r",
            """\'""" to "\'",
            """\"""" to "\"",
            """\\""" to "\\",
            """\{""" to "{",
            """\}""" to "}",
        )

        fun tokenize(source: String): MutableList<Token> {
            val tokens: MutableList<Token> = mutableListOf()
            var position = 0
            var span = Span.NONE
            var stringOpen = false

            while (position < source.length) {
                var error = true

                for (type in Type.entries) {
                    val matcher = type.regex.matcher(source.substring(position))

                    if (matcher.find()) {
                        if (!stringOpen && type == Type.QUOTE_START) {
                            stringOpen = true
                        } else if (!stringOpen && (type == Type.QUOTE_MIDDLE || type == Type.QUOTE_END)) {
                            continue
                        } else if (type == Type.QUOTE_END) {
                            stringOpen = false
                        }

                        val group = matcher.group()

                        val value = when (type) {
                            Type.QUOTE, Type.QUOTE_START, Type.QUOTE_MIDDLE, Type.QUOTE_END -> quote_replace.entries.fold(
                                group.substring(1, group.length - 1),
                            ) { string, (key, value) -> string.replace(key, value) }

                            Type.IDENTIFIER -> if (group.startsWith('`')) {
                                group.substring(1, group.length - 1)
                            } else {
                                group
                            }

                            else -> group
                        }

                        span = Span(position, position + group.length)

                        if (type != Type.WHITESPACE && type != Type.COMMENT) {
                            tokens.add(Token(value, type, span))
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
