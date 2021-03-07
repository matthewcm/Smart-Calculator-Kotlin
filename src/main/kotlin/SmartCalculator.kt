import java.io.ByteArrayInputStream
import java.lang.ArithmeticException
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

    private fun String.isNumber() : Boolean {
        return if (isNullOrEmpty()) false else all { Character.isDigit(it) }
    }

    private fun variableAssignment(line: String) {

        val (i, v) = line.split("=").map { it.trim() }

        try {
            if (i.isAVariable()){
                if (variables.containsKey(v)) variables[i] = variables[v]!!.toInt()
                else{
                    if (v.isNumber()) variables[i] = v.toInt()
                    else throw Exception()
                }
            }else throw Exception()

        }catch (e: Exception){
            println("Invalid assignment")
        }

    }

    private fun calculateSumOfString(nums: String): Int {
        return nums.split(" ").map { num ->
            when {
                num.isANegativeBinaryOperator() -> {
                    if (num.length % 2 == 1) subtractNextOperand()
                    else addNextOperand()
                }
                num.isAPositiveBinaryOperator() -> {
                    addNextOperand()
                }
                num.isAVariable() -> {
                    if (variables.containsKey(num)) performOperationOnOperand(variables[num]!!.toInt())
                    else throw ArithmeticException()
                }
                else -> {
                    performOperationOnOperand(num.toInt())
                }

            }
        }.reduce { numSum, num -> numSum + num }
    }

    private fun String.isANegativeBinaryOperator(): Boolean = toSet().size == 1 && toSet().first() == '-'
    private fun String.isAPositiveBinaryOperator(): Boolean = toSet().size == 1 && toSet().first() == '+'
    private fun String.isAVariable(): Boolean = matches(Regex("[a-zA-Z]+"))

    fun sum(nums: String) {
        try {
            val numsSum = calculateSumOfString(nums)

            println(numsSum)
        } catch (e: ArithmeticException) {
            println("Unknown variable")
        } catch (e: Exception) {
            println("Invalid Expression")
        }

    }

    private fun runCommand(command: String): Boolean {
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

    private fun printVariable(name: String) {
        if (variables.containsKey(name)) println(variables[name])
        else println("Unknown variable")
    }

    fun multiSum() {
        val scanner = Scanner(System.`in`)

        while (scanner.hasNextLine()) {
            val nextLine = scanner.nextLine()

            val line = nextLine.toString().trim()

            try {

                when {
                    line.first() == '/' -> {
                        if (!runCommand(line)) return
                    }
                    line.contains('=') -> variableAssignment(line)
                    line.isAVariable() -> {
                        printVariable(line)
                    }
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