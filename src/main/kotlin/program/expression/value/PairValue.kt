package program.expression.value

class PairValue(override val value: Pair<Value<*>, Value<*>>): Value<Pair<Value<*>, Value<*>>>(value)  {

    override fun getItem(name: String): Value<*> {
        return when (name) {
            "first" -> value.first
            "second" -> value.second
            else -> super.getItem(name)
        }
    }

    override fun typeString(): String {
        return "pair"
    }

}