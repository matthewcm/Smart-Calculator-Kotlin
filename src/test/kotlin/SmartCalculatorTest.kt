import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.io.ByteArrayInputStream

internal class SmartCalculatorTest {

    private var sc = SmartCalculator()

    @BeforeEach
    fun before() {
        sc = SmartCalculator()
    }

    @Test
    fun `Given '10 12' is input, When sum is invoked, then it should return 22`() {
//         Given
        val input = "5 6"
        val inp = ByteArrayInputStream(input.toByteArray());
        System.setIn(inp);

//        WHEN
        val sum = sc.sum()

//        THEN
        assertEquals(sum, 11)

    }
    @Test
    fun `Given '5 6' is input, When sum is called, then it should return 11`() {
//         Given
        val input = "5 6"
        val inp = ByteArrayInputStream(input.toByteArray());
        System.setIn(inp);

//        WHEN
        val sum = sc.sum()

//        THEN
        assertEquals(sum, 11)

    }

}