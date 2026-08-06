package program.expression.value.nullvalue

class Null {
    companion object {
        val VALUE = NullValue(Null())
    }

    override fun toString(): String {
        return "null"
    }
}
