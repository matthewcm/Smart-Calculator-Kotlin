import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
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

        val input = "5 6"
        val inp = ByteArrayInputStream(input.toByteArray());
        System.setIn(inp);


// test stuff here...


// test stuff here...

//        WHEN
        sc.sum()
        val sum = myOut.toString()

//        THEN
        assertEquals(11, sum)

    }
    @Test
    fun `Given '5 6' is input, When sum is called, then it should return 11`() {
//         Given
        val input = "5 6"
        val inp = ByteArrayInputStream(input.toByteArray());
        System.setIn(inp);

//        WHEN
        sc.sum()
        val sum = myOut.toString()

//        THEN
        assertEquals(11, sum)

    }

}