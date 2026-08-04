package program.expression.value

interface Static : Callable {
    val name: String

    fun getItem(name: String): Value<*>? {
        return null
    }

    companion object {
        val staticCompanions: MutableMap<String, Static> = mutableMapOf()

        fun register(static: Static) {
            staticCompanions[static.name] = static
        }

        fun initialize() {
            register(RangeValue.Companion)
            register(PairValue.Companion)
            register(MapValue.Companion)
        }
    }
}
