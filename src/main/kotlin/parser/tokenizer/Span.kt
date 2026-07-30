package parser.tokenizer

class Span(val start: Int, val end: Int) {
    companion object {
        val NONE = Span(0, 0)
    }

    override fun toString(): String {
        return "$start, $end"
    }
}
