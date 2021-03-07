import java.io.ByteArrayInputStream
import java.util.*

class SmartCalculator {

    fun sum(nums: String) {

        var operation = 1

        fun String.isNegativeBinaryOperator(): Boolean = toSet().size == 1 && toSet().first() == '-'
        fun String.isPositiveBinaryOperator(): Boolean = toSet().size == 1 && toSet().first() == '+'

        val sumOfNums = nums.split(" ").map { num ->
            when {
                num.isNegativeBinaryOperator() -> {
                    if (num.length % 2 == 1) operation *= -1
                    else operation = 1
                    0
                }
                num.isPositiveBinaryOperator() -> {
                    operation = 1
                    0
                }
                else -> {
                    val result = operation * num.toInt()
                    // Reset operation to add
                    operation = 1
                    result
                }

            }
        }.reduce{numSum, num -> numSum + num}

        println(sumOfNums)
    }

    fun multiSum() {
        val scanner = Scanner(System.`in`)

        loop@ while (scanner.hasNextLine()) {
            val nextLine = scanner.nextLine()

            when (val line = nextLine.toString()) {
                "/exit" -> {
                    println("Bye!")
                    return
                }
                "/help" -> {
                    println("The program calculates the sum of numbers")
                    println("Supports + and - operations.")
                    println("An even number of - is +. -- = +")
                }
                "" -> continue@loop
                else -> {
                    sum(line)
                }
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