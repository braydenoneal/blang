package testing.test

import program.expression.value.StringValue

class Strings : Test() {
    override fun body(): String {
        return """
            a = "a"
            b = 'b'
            c = a + b
            d = "1" + 6
            e = "1\"2\{3{2 + 2}5\"6\}7{4 + 4}9\"A\}B"
            f = "1\"2\{3{2 + 2}5\"6\}7"
            slice = "0123456"
            sliceMiddle = slice[1:4]
            sliceStart = slice[:4]
            sliceEnd = slice[1:]
            sliceAll = slice[:]
        """.trimIndent()
    }

    override fun expects(): List<Expect> {
        return listOf(
            Expect("a", StringValue("a")),
            Expect("b", StringValue("b")),
            Expect("c", StringValue("ab")),
            Expect("d", StringValue("16")),
            Expect("e", StringValue("1\"2{345\"6}789\"A}B")),
            Expect("f", StringValue("1\"2{345\"6}7")),
            Expect("sliceMiddle", StringValue("123")),
            Expect("sliceStart", StringValue("0123")),
            Expect("sliceEnd", StringValue("12345")),
            Expect("sliceAll", StringValue("012345")),
        )
    }
}
