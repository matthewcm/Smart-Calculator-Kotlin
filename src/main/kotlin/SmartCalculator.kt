import java.io.ByteArrayInputStream
import java.lang.Exception
import java.util.*

class SmartCalculator {

    private var variables = mutableMapOf<String, Int>()

    private var sign = 1

    private fun subtractNextOperand(): Int {
        sign = -1
        return 0
    }

    private fun addNextOperand(): Int {
        sign = 1
        return 0
    }

    private fun performOperationOnOperand(num: Int): Int {
        val result = sign * num
        sign = 1
        return result
    }

    private fun variableAssignment(line: String) {

        val (i, v) = line.split("=").map { it.trim() }

        variables[i] = v.toInt()

    }

    private fun calculateSumOfString(nums: String): Int {
        return nums.split(" ").map { num ->
            when {
                num.isNegativeBinaryOperator() -> {
                    if (num.length % 2 == 1) subtractNextOperand()
                    else addNextOperand()
                }
                num.isPositiveBinaryOperator() -> {
                    addNextOperand()
                }
                else -> {
                    performOperationOnOperand(num.toInt())
                }

            }
        }.reduce { numSum, num -> numSum + num }
    }

    private fun String.isNegativeBinaryOperator(): Boolean = toSet().size == 1 && toSet().first() == '-'
    private fun String.isPositiveBinaryOperator(): Boolean = toSet().size == 1 && toSet().first() == '+'

    fun sum(nums: String) {
        try {
            val numsSum = calculateSumOfString(nums)

            println(numsSum)
        } catch (e: Exception) {
            println("Invalid Expression")
        }

    }

    private fun runCommand(command: String):Boolean {
        when (command) {
            "/exit" -> {
                println("Bye!")
                return false
            }
            "/help" -> {
                println("The program calculates the sum of numbers")
                println("Supports + and - operations.")
                println("An even number of - is +. -- = +")
            }
            else -> println("Unknown Command")
        }

        return true

    }

    fun multiSum() {
        val scanner = Scanner(System.`in`)

        while (scanner.hasNextLine()) {
            val nextLine = scanner.nextLine()

            val line = nextLine.toString()

            try {

                when {
                    line.first() == '/' -> {
                        if (!runCommand(line)) return
                    }
                    line.contains('=') -> variableAssignment(line)
                    else -> sum(line)
                }
            } catch (e: Exception) {
                continue
            }
        }

    }
}

fun main() {
    val input = "10 12\n5 6\n00"
    val inp = ByteArrayInputStream(input.toByteArray())
    System.setIn(inp)

    val sc = SmartCalculator()
    sc.multiSum()

}