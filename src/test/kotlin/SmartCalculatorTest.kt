import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream

internal class SmartCalculatorTest {

    private var sc = SmartCalculator()
    private val myOut = ByteArrayOutputStream()


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
        sc.sum(intArrayOf( x,y))
        val sum = myOut.toString().trim().toInt()

//        THEN
        assertEquals(22, sum)

    }
    @Test
    fun `Given 'exit' is input, When multisum is invoked, then it should exit the program`() {
//         Given
        val input = "/exit"
        val inp = ByteArrayInputStream(input.toByteArray())
        System.setIn(inp)


//        WHEN
        sc.multiSum()
        val result = myOut.toString().trim()

//        THEN
        assertEquals("Bye!",result)

    }
    @Test
    fun `Given 'help' is input, When multisum is invoked, then it should print information about the program`() {
//         Given
        val input = "/help"
        val inp = ByteArrayInputStream(input.toByteArray())
        System.setIn(inp)


//        WHEN
        sc.multiSum()
        val result = myOut.toString().trim()

//        THEN
        assertEquals("Bye!",result)

    }
    @Test
    fun `Given '10 12 13' is input, When sum is invoked, then it should print 35`() {
//         Given
        val x = 10
        val y = 12
        val z = 13


//        WHEN
        sc.sum(intArrayOf(x,y,z))
        val sum = myOut.toString().trim().toInt()

//        THEN
        assertEquals(35, sum)

    }

    @Test
    fun `Given '10 12' '5 6' as two inputs, When multiSum is invoked, then it should print 22 and print 11`() {
//         Given
        val input = "10 12\n5 6"
        val inp = ByteArrayInputStream(input.toByteArray())
        System.setIn(inp)

//        WHEN
        sc.multiSum()


        val (a,b) = myOut.toString()
            .trim()
            .lines()
            .map{it.trim()}
            .map{it.toInt()}

//        THEN
        assertEquals( 22, a)
        assertEquals( 11, b)

    }

    @Test
    fun `Given '10 12 13' '5 6 5 6' as two inputs, When multiSum is invoked, then it should print 35 and print 22`() {
//         Given
        val input = "10 12 13\n5 6 5 6"
        val inp = ByteArrayInputStream(input.toByteArray())
        System.setIn(inp)

//        WHEN
        sc.multiSum()


        val (a,b) = myOut.toString()
            .trim()
            .lines()
            .map{it.trim()}
            .map{it.toInt()}

//        THEN
        assertEquals( 35, a)
        assertEquals( 22, b)

    }

}