package testing.test

import program.expression.value.BooleanValue
import program.expression.value.IntegerValue
import program.expression.value.ListValue
import program.expression.value.StringValue

class Lists : Test() {
    override fun body(): String {
        return """
            list = [false, 0, ""]
            list2 = [0]
            list2.append(2)
            list2.insert(1, 1)
            list2.pop()
            list2.remove(1)
            contains = 0 in list2
            notContains = 1 in list2
            containsAll = list.containsAll([false, 0])
            notContainsAll = list.containsAll([false, 0, 1])
            list3 = [0]
            list3 += [1]
            listOfList = [0, [0, 0]]
            listOfList[1][1] = 1
            nested = listOfList[1][1]
            nameless = [[[0]]][0][0][0]
            length = [0, 1, 2].size
            nestedCall = [0, [0]]
            nestedCall[1].append(2)
            nestedCall[1].insert(1, 1)
            nestedCall[1].pop()
            nestedCall[1].remove(1)
            negativeIndex = [0, 1, 2][-1]
            negativeIndex1 = [0, 1, 2][-3]
            negativeIndex2 = [0, 1, 2][-5]
            slice = [0, 1, 2, 3, 4, 5, 6]
            sliceMiddle = slice[1:4]
            sliceStart = slice[:4]
            sliceEnd = slice[1:]
            sliceAll = slice[:]
        """.trimIndent()
    }

    override fun expects(): List<Expect> {
        return listOf(
            Expect("list", ListValue(mutableListOf(BooleanValue(false), IntegerValue(0), StringValue("")))),
            Expect("list2", ListValue(mutableListOf(IntegerValue(0)))),
            Expect("contains", BooleanValue(true)),
            Expect("notContains", BooleanValue(false)),
            Expect("containsAll", BooleanValue(true)),
            Expect("notContainsAll", BooleanValue(false)),
            Expect("list3", ListValue(mutableListOf(IntegerValue(0), IntegerValue(1)))),
            Expect("nested", IntegerValue(1)),
            Expect("nameless", IntegerValue(0)),
            Expect("length", IntegerValue(3)),
            Expect("nestedCall", ListValue(mutableListOf(IntegerValue(0), ListValue(mutableListOf(IntegerValue(0)))))),
            Expect("negativeIndex", IntegerValue(2)),
            Expect("negativeIndex1", IntegerValue(0)),
            Expect("negativeIndex2", IntegerValue(1)),
            Expect("sliceMiddle", ListValue(mutableListOf(IntegerValue(1), IntegerValue(2), IntegerValue(3)))),
            Expect("sliceStart", ListValue(mutableListOf(IntegerValue(0), IntegerValue(1), IntegerValue(2), IntegerValue(3)))),
            Expect("sliceEnd", ListValue(mutableListOf(IntegerValue(1), IntegerValue(2), IntegerValue(3), IntegerValue(4), IntegerValue(5)))),
            Expect("sliceAll", ListValue(mutableListOf(IntegerValue(0), IntegerValue(1), IntegerValue(2), IntegerValue(3), IntegerValue(4), IntegerValue(5)))),
        )
    }
}
