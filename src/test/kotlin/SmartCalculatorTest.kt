import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream

internal class SmartCalculatorTest {

    private var sc = SmartCalculator()
    var out = ByteArrayOutputStream()

    @BeforeEach
    fun before() {
        sc = SmartCalculator()

        out = ByteArrayOutputStream()
        System.setOut(PrintStream(out))
    }

    @Test
    fun `Given '10 12' is input, When sum is invoked, then it should return 22`() {
//         Given
        val x = 10
        val y = 12


//        WHEN
        sc.sum(x,y)
        val sum = out.toString().trim().toInt()

//        THEN
        assertEquals(22, sum)

    }
    @Test
    fun `Given '10 12' '5 6' as two inputs, When multiSum is invoked, then it should print 22 and print 11`() {
//         Given
        val input = "10 12\n5 6\n"
        val inp = ByteArrayInputStream(input.toByteArray());
        System.setIn(inp);

//        WHEN
        sc.multiSum()
        var (a,b) = (out.toString().trim().split("\n").map{it.toInt()})

//        THEN
        assertEquals( 22, a)
        assertEquals( 11, b)

    }

}