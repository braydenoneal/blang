package program

import parser.tokenizer.Span

class RunException(message: String, span: Span = Span.NONE) : ProgramException(message, span)
