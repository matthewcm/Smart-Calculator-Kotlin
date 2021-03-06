import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.lang.Exception

internal class SmartCalculatorTest {

    @ExperimentalStdlibApi
    private var sc = SmartCalculator()
    private val myOut = ByteArrayOutputStream()


    @ExperimentalStdlibApi
    @BeforeEach
    fun before() {
        System.setOut(PrintStream(myOut))
        sc = SmartCalculator()
    }


    @ExperimentalStdlibApi
    @Test
    fun `Given 'exit' is input, When multisum is invoked, then it should exit the program`() {
//         Given
        val input = "/exit\n5 + 6"
        val inp = ByteArrayInputStream(input.toByteArray())
        System.setIn(inp)


//        WHEN
        sc.multiSum()
        val result = myOut.toString().trim()

//        THEN
        assertEquals("Bye!", result)

    }

    @ExperimentalStdlibApi
    @Test
    fun `Given 'help' is input, When multisum is invoked, then it should print information about the program`() {
//         Given
        val input = "/help"
        val inp = ByteArrayInputStream(input.toByteArray())
        System.setIn(inp)


//        WHEN
        sc.multiSum()
        val result = myOut.toString().trim().lines()[0]

//        THEN
        assertEquals("The program calculates the sum of numbers", result)

    }

    @ExperimentalStdlibApi
    @Test
    fun `Given 'help' is input, When multisum is invoked, then it should print information about the program and continue`() {
//         Given
        val input = "/help\n5 + 6"
        val inp = ByteArrayInputStream(input.toByteArray())
        System.setIn(inp)


//        WHEN
        sc.multiSum()
        val result = myOut.toString().trim().lines()

//        THEN
        assertEquals("The program calculates the sum of numbers", result.first())
        assertEquals("11", result.last())

    }

    @ExperimentalStdlibApi
    @Test
    fun `Given '10 12 13' is input, When sum is invoked, then it should print 35`() {
//         Given
        val x = 10
        val y = 12
        val z = 13


//        WHEN
        sc.sum("$x + $y + $z")
        val sum = myOut.toString().trim().toInt()

//        THEN
        assertEquals(35, sum)

    }

    @ExperimentalStdlibApi
    @Test
    fun `Given '10 - 12 -- 13' is input, When sum is invoked, then it should print 11`() {
//         Given
        val x = 10
        val y = 12
        val z = 13


//        WHEN
        sc.sum("$x - $y -- $z")
        val sum = myOut.toString().trim().toInt()

//        THEN
        assertEquals(11, sum)

    }

    @ExperimentalStdlibApi
    @Test
    fun `Given '10 --- 12' is input, When sum is invoked, then it should print -2`() {
//         Given
        val x = 10
        val y = 12


//        WHEN
        sc.sum("$x --- $y")
        val sum = myOut.toString().trim().toInt()

//        THEN
        assertEquals(-2, sum)

    }

    @ExperimentalStdlibApi
    @Test
    fun `Given '---10' is input, When sum is invoked, then it should print -10`() {
//         Given
        val x = 10


//        WHEN
        sc.sum("--- $x")
        val sum = myOut.toString().trim().toInt()

//        THEN
        assertEquals(-10, sum)

    }

    @ExperimentalStdlibApi
    @Test
    fun `Given '10 + 12' '5 + 6' as two inputs, When multiSum is invoked, then it should print 22 and print 11`() {
//         Given
        val input = "10 + 12\n5 + 6"
        val inp = ByteArrayInputStream(input.toByteArray())
        System.setIn(inp)

//        WHEN
        sc.multiSum()


        val (a, b) = myOut.toString()
            .trim()
            .lines()
            .map { it.trim() }
            .map { it.toInt() }

//        THEN
        assertEquals(22, a)
        assertEquals(11, b)

    }

    @ExperimentalStdlibApi
    @Test
    fun `Given '10 + 12 + 13' '5 + 6 + 5 + 6' as two inputs, When multiSum is invoked, then it should print 35 and print 22`() {
//         Given
        val input = "10 + 12 + 13\n5 + 6 + 5 + 6"
        val inp = ByteArrayInputStream(input.toByteArray())
        System.setIn(inp)

//        WHEN
        sc.multiSum()


        val (a, b) = myOut.toString()
            .trim()
            .lines()
            .map { it.trim() }
            .map { it.toInt() }

//        THEN
        assertEquals(35, a)
        assertEquals(22, b)

    }

    @ExperimentalStdlibApi
    @Test
    fun `Given '' '' as two inputs, When multiSum is invoked, then it should not print anything`() {
//         Given
        val input = "\n\n"
        val inp = ByteArrayInputStream(input.toByteArray())
        System.setIn(inp)

//        WHEN
        sc.multiSum()

//        THEN
        val result = myOut.toString()
        assertEquals("", result)

    }

    @ExperimentalStdlibApi
    @Test
    fun `Given '10abc' '' as two inputs, When multiSum is invoked, then it should print Invalid expression`() {
//         Given
        val input = "10abc\n\n"
        val inp = ByteArrayInputStream(input.toByteArray())
        System.setIn(inp)

//        WHEN
        sc.multiSum()

//        THEN
        val result = myOut.toString().trim()
        assertEquals("Invalid expression", result)

    }

    @ExperimentalStdlibApi
    @Test
    fun `Given '10+' is input, When sum is invoked, then it should Invalid expression`() {
//         Given
        val input = "10+"


//        WHEN
        sc.sum(input)

//        THEN
        val sum = myOut.toString().trim()
        assertEquals("Invalid expression", sum)

    }

    @ExperimentalStdlibApi
    @Test
    fun `Given command 'abc', When multiSum is invoked, then it should print Unknown command`() {
//         Given
        val input = "/abc\n\n"
        val inp = ByteArrayInputStream(input.toByteArray())
        System.setIn(inp)

//        WHEN
        sc.multiSum()

//        THEN
        val result = myOut.toString().trim()
        assertEquals("Unknown command", result)

    }

    @ExperimentalStdlibApi
    @Test
    fun `Given input 'n = 10' 'n' , When multiSum is invoked, then it should print 10`() {
//         Given
        val input = "n = 10\n n\n"
        val inp = ByteArrayInputStream(input.toByteArray())
        System.setIn(inp)

//        WHEN
        sc.multiSum()

//        THEN
        val result = myOut.toString().trim().toInt()
        assertEquals(10, result)

    }
    @ExperimentalStdlibApi
    @Test
    fun `Given input 'n =10' 'n' , When multiSum is invoked, then it should print 10`() {
//         Given
        val input = "n  =10\n n\n"
        val inp = ByteArrayInputStream(input.toByteArray())
        System.setIn(inp)

//        WHEN
        sc.multiSum()

//        THEN
        val result = myOut.toString().trim().toInt()
        assertEquals(10, result)

    }
    @ExperimentalStdlibApi
    @Test
    fun `Given input 'n = -10' 'n' , When multiSum is invoked, then it should print 10`() {
//         Given
        val input = "n  = -10\n n\n"
        val inp = ByteArrayInputStream(input.toByteArray())
        System.setIn(inp)

//        WHEN
        sc.multiSum()

//        THEN
        val result = myOut.toString().trim().toInt()
        assertEquals(-10, result)

    }
    @ExperimentalStdlibApi
    @Test
    fun `Given input 'MINI =10' 'MINI' , When multiSum is invoked, then it should print 10`() {
//         Given
        val input = "MINI  =10\n MINI\n"
        val inp = ByteArrayInputStream(input.toByteArray())
        System.setIn(inp)

//        WHEN
        sc.multiSum()

//        THEN
        val result = myOut.toString().trim().toInt()
        assertEquals(10, result)

    }

    @ExperimentalStdlibApi
    @Test
    fun `Given input 'n =10' 'b = n' 'b' , When multiSum is invoked, then it should print 10`() {
//         Given
        val input = "n  =10\nb=n \n b"
        val inp = ByteArrayInputStream(input.toByteArray())
        System.setIn(inp)

//        WHEN
        sc.multiSum()

//        THEN
        val result = myOut.toString().trim().toInt()
        assertEquals(10, result)

    }
    @ExperimentalStdlibApi
    @Test
    fun `Given input 'n =10' 'b' , When multiSum is invoked, then it should print Unknown variable`() {
//         Given
        val input = "n  =10\n b"
        val inp = ByteArrayInputStream(input.toByteArray())
        System.setIn(inp)

//        WHEN
        sc.multiSum()

//        THEN
        val result = myOut.toString().trim()
        assertEquals("Unknown variable", result)

    }
    @ExperimentalStdlibApi
    @Test
    fun `Given input 'n =10' 'b = 5' 'a + b' , When multiSum is invoked, then it should print 15`() {
//         Given
        val input = "n = 10\n b = 5\n n + b"
        val inp = ByteArrayInputStream(input.toByteArray())
        System.setIn(inp)

//        WHEN
        sc.multiSum()

//        THEN
        val result = myOut.toString().trim().toInt()
        assertEquals(15, result)

    }
    @ExperimentalStdlibApi
    @Test
    fun `Given input 'n =10' 'b = 5' 'a + b' , When multiSum is invoked, then it should print Unknown variable`() {
//         Given
        val input = "n = 10\n b = 5\n a + b"
        val inp = ByteArrayInputStream(input.toByteArray())
        System.setIn(inp)

//        WHEN
        sc.multiSum()

//        THEN
        val result = myOut.toString().trim()
        assertEquals("Unknown variable", result)

    }
    @ExperimentalStdlibApi
    @Test
    fun `Given input 'var1 = 10' , When multiSum is invoked, then it should print Invalid assignment`() {
//         Given
        val input = "var1 = 10"
        val inp = ByteArrayInputStream(input.toByteArray())
        System.setIn(inp)

//        WHEN
        sc.multiSum()

//        THEN
        val result = myOut.toString().trim()
        assertEquals("Invalid assignment", result)

    }

    @ExperimentalStdlibApi
    @Test
    fun `Given input 'var = 10b0' , When multiSum is invoked, then it should print Invalid assignment`() {
//         Given
        val input = "var = 10b0"
        val inp = ByteArrayInputStream(input.toByteArray())
        System.setIn(inp)

//        WHEN
        sc.multiSum()

//        THEN
        val result = myOut.toString().trim()
        assertEquals("Invalid assignment", result)

    }

    @ExperimentalStdlibApi
    @Test
    fun `Given input is '1 + 2',When toPostfix is invoked, convert to '1 2 +`(){

        val input = "1 + 2"

        val postfix = sc.toPostfix(input)

        assertEquals("1 2 + ", postfix)

    }
    @ExperimentalStdlibApi
    @Test
    fun `Given input is '1 - 2',When toPostfix is invoked, convert to '1 2 -`(){

        val input = "1 - 2"

        val postfix = sc.toPostfix(input)

        assertEquals("1 2 - ", postfix)

    }
    @ExperimentalStdlibApi
    @Test
    fun `Given input is '1 - 2 + 3',When toPostfix is invoked, convert to '1 2 - 3 + `(){

        val input = "1 - 2 + 3"

        val postfix = sc.toPostfix(input)

        assertEquals("1 2 - 3 + ", postfix)

    }
    @ExperimentalStdlibApi
    @Test
    fun `Given input is '1 - 2 * 3',When toPostfix is invoked, convert to '1 2 3 * - `(){

        val input = "1 - 2 * 3"

        val postfix = sc.toPostfix(input)

        assertEquals("1 2 3 * - ", postfix)

    }
    @ExperimentalStdlibApi
    @Test
    fun `Given input is '(1 - 2) * 3',When toPostfix is invoked, convert to '1 2 - 3 *  `(){

        val input = "(1 - 2) * 3"

        val postfix = sc.toPostfix(input)

        assertEquals("1 2 - 3 * ", postfix)

    }

    @ExperimentalStdlibApi
    @Test
    fun `Given input is '(-1 - 2) * 3',When toPostfix is invoked, convert to '1 2 - 3 *  `(){

        val input = "(-1 - 2) * 3"

        val postfix = sc.toPostfix(input)

        assertEquals("-1 2 - 3 * ", postfix)

    }

    @ExperimentalStdlibApi
    @Test
    fun `Given input is '8 * 3 + 12 * (4 - 2)',When toPostfix is invoked, convert to '1 2 - 3 *  `(){

        val infix = "8 * 3 + 12 * (4 - 2)"

        val postfix= sc.toPostfix(infix)


        assertEquals("8 3 * 12 4 2 - * + ", postfix)

    }

    @ExperimentalStdlibApi
    @Test
    fun `Given input is '-1 2 - 3 * ',When sum2 is invoked, calculate -9 `(){

        val input = "-1 2 - 3 * "

        val sum = sc.sumOfPostfix(input)

        assertEquals(-9, sum )

    }

    @ExperimentalStdlibApi
    @Test
    fun `Given input is '8 * 3 + 12 * (4 - 2)',When postfix & sum2 is invoked, calculate 48 `(){

        val infix = "8 * 3 + 12 * (4 - 2)"
        val postfix=sc.toPostfix(infix)
        assertEquals("8 3 * 12 4 2 - * + ", postfix)

        val sum = sc.sumOfPostfix(postfix)

        assertEquals(48, sum )

    }

    @ExperimentalStdlibApi
    @Test
    fun `Given input is '91 div 13',When postfix & sum2 is invoked, calculate 7 `(){

        val infix = "91 / 13"
        val postfix=sc.toPostfix(infix)
        assertEquals("91 13 / ", postfix)

        val sum = sc.sumOfPostfix(postfix)

        assertEquals(7, sum )

    }
    @ExperimentalStdlibApi
    @Test
    fun `Given input is '2 ^ 8',When postfix & sum2 is invoked, calculate 256`(){

        val infix = "2 ^ 8"
        val postfix=sc.toPostfix(infix)
        assertEquals("2 8 ^ ", postfix)

        val sum = sc.sumOfPostfix(postfix)

        assertEquals(256, sum )

    }
    @ExperimentalStdlibApi
    @Test
    fun `Given input '8 * (2 + 3' , When multiSum is invoked, then it should print Invalid expression`() {
//         Given
        val input = "8 * (2 + 3"
        val inp = ByteArrayInputStream(input.toByteArray())
        System.setIn(inp)

//        WHEN
        sc.multiSum()

//        THEN
        val result = myOut.toString().trim()
        assertEquals("Invalid expression", result)

    }
    @ExperimentalStdlibApi
    @Test
    fun `Given input is '8 * (2 + 3',When postfix is invoked, throw`(){

        val infix = "8 * (2 + 3"
        try {
            val postfix=sc.toPostfix(infix)
            assertEquals(Unit, postfix)
        }catch(e:Exception){
            assert(true)

        }


    }

}