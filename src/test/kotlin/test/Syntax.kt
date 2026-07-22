package testing.test

import program.expression.value.IntegerValue
import program.expression.value.ListValue

class Syntax : Test() {
    override fun body(): String {
        return """

  a   =    (2);b=1+2 ;

fn   AdDnUmBeR  (   num  )  {   a+=num

  b   +=   num;return} AdDnUmBeR  (   2 );  c   =[0
,   1,2,3   ,

4
,]

        """
    }

    override fun expects(): List<Expect> {
        return listOf(
            Expect("a", IntegerValue(4)),
            Expect("b", IntegerValue(5)),
            Expect(
                "c",
                ListValue(
                    mutableListOf(
                        IntegerValue(0),
                        IntegerValue(1),
                        IntegerValue(2),
                        IntegerValue(3),
                        IntegerValue(4),
                    ),
                ),
            ),
        )
    }
}
