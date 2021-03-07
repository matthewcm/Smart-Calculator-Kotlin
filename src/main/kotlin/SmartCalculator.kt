import java.io.ByteArrayInputStream
import java.lang.Exception
import java.util.*

class SmartCalculator {

    fun sum(nums: String) {

        var operation = 1

        fun String.isNegativeBinaryOperator(): Boolean = toSet().size == 1 && toSet().first() == '-'
        fun String.isPositiveBinaryOperator(): Boolean = toSet().size == 1 && toSet().first() == '+'

        fun subtractNextOperand():Int {
            operation = -1
            return 0
        }
        fun addNextOperand():Int {
            operation = 1
            return 0
        }
        fun performOperationOnOperand(num: Int):Int {
            val result = operation * num
            operation = 1
            return result
        }

        try {
            val sumOfNums = nums.split(" ").map { num ->
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

            }.reduce{numSum, num -> numSum + num}
            println(sumOfNums)
        }catch(e:Exception){
            println("Invalid Expression")
        }

    }

    fun multiSum() {
        val scanner = Scanner(System.`in`)

        while (scanner.hasNextLine()) {
            val nextLine = scanner.nextLine()

            val line = nextLine.toString()

            try {

                when {
                    line.first() == '/' -> {
                        when (line) {
                            "/exit" -> {
                                println("Bye!")
                                return
                            }
                            "/help" -> {
                                println("The program calculates the sum of numbers")
                                println("Supports + and - operations.")
                                println("An even number of - is +. -- = +")
                            }
                            else -> println("Unknown Command")
                        }
                    }
                    else -> {
                        sum(line)
                    }
                }
            }
            catch (e:Exception){
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