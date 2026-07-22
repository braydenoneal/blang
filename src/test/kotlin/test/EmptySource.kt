package testing.test

class EmptySource : Test() {
    override fun body(): String {
        return ""
    }

    override fun expects(): List<Expect> {
        return listOf()
    }
}
