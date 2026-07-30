package program.expression.value.util

class Range(val start: Int, val end: Int, val step: Int) {
    override fun toString(): String {
        return "range(start = $start, end = $end, step = $step)"
    }
}
