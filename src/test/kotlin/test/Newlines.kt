package testing.test

class Newlines : Test() {
    override fun body(): String {
        return "\n\n\n\n"
    }

    override fun expects(): List<Expect> {
        return listOf()
    }
}
