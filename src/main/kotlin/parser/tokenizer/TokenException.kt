package parser.tokenizer

import program.ProgramException

class TokenException(message: String, span: Span = Span.NONE) : ProgramException(message, span)
