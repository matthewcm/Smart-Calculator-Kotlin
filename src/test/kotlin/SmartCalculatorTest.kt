import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream

internal class SmartCalculatorTest {

    private var sc = SmartCalculator()
    val myOut = ByteArrayOutputStream()


    @BeforeEach
    fun before() {
        System.setOut(PrintStream(myOut))
        sc = SmartCalculator()
    }

    @Test
    fun `Given '10 12' is input, When sum is invoked, then it should print 22`() {
//         Given
        val x = 10
        val y = 12


//        WHEN
        sc.sum(x,y)
        val sum = myOut.toString().trim().toInt()

//        THEN
        assertEquals(sum, 22)

    }

    @Test
    fun `Given '10 12' '5 6' as two inputs, When multiSum is invoked, then it should print 22 and print 11`() {
//         Given
        val input = "10 12\n5 6"
        val inp = ByteArrayInputStream(input.toByteArray());
        System.setIn(inp);

//        WHEN
        sc.multiSum()


        var (a,b) = myOut.toString()
            .trim()
            .lines()
            .map{it.trim()}
            .map{it.toInt()}

//        THEN
        assertEquals( 22, a)
        assertEquals( 11, b)

    }

}