package parser

import parser.tokenizer.Span
import program.ProgramException

class ParseException(message: String, span: Span = Span.NONE) : ProgramException(message, span)
