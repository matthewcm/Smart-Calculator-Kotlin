import java.io.ByteArrayInputStream
import java.lang.ArithmeticException
import java.lang.Exception
import java.util.*

@ExperimentalStdlibApi
class SmartCalculator {
    private var variables = mutableMapOf<String, Int>()
    private var sign = 1
    var postfixResult = ""

    private fun subtract(a:Int, b:Int):Int {
        return a - b
    }

    @ExperimentalStdlibApi
    fun MutableList<Int>.performOperation(operator:String):Int {
        return when (operator) {
            "*" -> removeLast() * removeLast()
            "/" -> removeLast() / removeLast()
            "-" -> {
                if (size == 1) - removeLast()
                else subtract(b=removeLast(), a=removeLast())
            }
            "+" -> removeLast() + removeLast()
            else -> 0
        }
    }
    @ExperimentalStdlibApi
    fun sumOfPostfix(elements: String):Int {
        val stack = mutableListOf<Int>()

        elements
            .trim()
            .split(" ")
            .map{it.trim()}
            .forEach{element ->
                when {
                    element.isAVariable() -> {
                        if (variables.containsKey(element)) stack.add(variables[element]!!.toInt())
                        else throw ArithmeticException()
                    }
                    element.isOperator() -> {
                        stack.add(stack.performOperation(element))
                    }
                    else -> stack.add(element.toInt())
                }
            }

        return stack.last()

    }

    fun sum(input: String) {
        try {
            val postfix = toPostfix(input)
            val sumOfInput= sumOfPostfix(postfix)

            println(sumOfInput)
        } catch (e: ArithmeticException) {
            println("Unknown variable")
        } catch (e: Exception) {
            println("Invalid Expression")
        }
    }

    @ExperimentalStdlibApi
    fun multiSum() {
        val scanner = Scanner(System.`in`)

        while (scanner.hasNextLine()) {
            val nextLine = scanner.nextLine()

            val line = nextLine.toString().trim()

            try {

                when {
                    line.first() == '/' -> if (!runCommand(line)) return
                    line.contains('=') -> variableAssignment(line)
                    line.isAVariable() -> printVariable(line)
                    else -> sum(line)
                }
            } catch (e: Exception) {
                continue
            }
        }

    }

    @ExperimentalStdlibApi
    fun MutableList<String>.popToPostfixResult():String {
        var last = removeLast()
        postfixResult += "$last "
        return last
    }

    private fun MutableList<String>.nextHasHigherPrecedenceThan(operator :String):Boolean {
        val top = this.last()
        return when {
            top.matches(Regex("[/*]")) -> {
                when {
                    operator.matches(Regex("[()]")) -> false
                    operator.matches(Regex("[-+]")) -> true
                    else -> true
                }
            }
            top.matches(Regex("[-+]")) -> {
                !operator.matches(Regex("[/*()]"))
            }
            else -> true
        }

    }
    private fun String.isMinusUnaryOperator(): Boolean = toSet().size == 1 && toSet().first() == '-'
    private fun String.isPositiveUnaryOperator(): Boolean = toSet().size == 1 && toSet().first() == '+'

    @ExperimentalStdlibApi
    fun toPostfix(input: String):String {
        var stack = mutableListOf<String>()

        val reg = Regex("(?<=[0-9a-zA-Z ])(?=[-+()*\\/])|(?<=[-+()*\\/])(?=[0-9a-zA-Z ])")

        val inputList = input
                .split(reg)
                .map{it.trim()}
                .map{
                    if(it.isMinusUnaryOperator()) {
                        if (it.length % 2 == 0){
                            "+"
                        }
                        else "-"
                    }else if (it.isPositiveUnaryOperator()) "+"
                    else it
                }
                .flatMap { x ->
                    if (x.isOperatorGroup()) x.split("").map{it.trim()}.filterNot{it == ""}
                    else listOf(x)
                }

//        println(inputList )

        inputList
            .forEach{element ->
                when {
                    (element.isAVariable() || element.isNumber()) ->
                        postfixResult += "$element "
                    element.isOperator() ->
                        when {

                            element.isUnaryMinusForStack() -> postfixResult += "$element"

                            stack.isEmpty() || stack.last() == "(" -> stack.add(element)

                            element == "(" -> stack.add(element)

                            element == ")" -> {
                                do { stack.popToPostfixResult() }
                                while(stack.last() != "(" )
                                stack.removeLast()
                            }

                            !stack.nextHasHigherPrecedenceThan(element) -> stack.add(element)

                            stack.nextHasHigherPrecedenceThan(element) -> {
                                stack.popToPostfixResult()
                                stack.add(element)
                            }
                        }
                }
            }

        while (stack.size > 0){
            stack.popToPostfixResult()
        }
        return postfixResult
    }


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

    private fun calculateSumOfString(nums: String): Int {
        return nums.split(" ").map { num ->
            when {
                num.isPositiveUnaryOperator() -> addNextOperand()
                num.isMinusUnaryOperator() -> {
                    if (num.length % 2 == 1) subtractNextOperand()
                    else addNextOperand()
                }
                num.isAVariable() -> {
                    if (variables.containsKey(num)) performOperationOnOperand(variables[num]!!.toInt())
                    else throw ArithmeticException()
                }
                else -> performOperationOnOperand(num.toInt())

            }
        }.reduce { numSum, num -> numSum + num }
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
                println("Supports / and * operations.")
                println("Supports () parenthesis.")
            }
            else -> println("Unknown Command")
        }

        return true

    }

    private fun printVariable(name: String) {
        if (variables.containsKey(name)) println(variables[name])
        else println("Unknown variable")
    }

    private fun String.isAVariable(): Boolean = matches(Regex("[a-zA-Z]+"))
    private fun String.isOperator(): Boolean = matches(Regex("[+\\-*/()]+"))
    private fun String.isOperatorGroup(): Boolean = matches(Regex("[+\\-*/() ]+"))
    private fun String.isNumber() : Boolean {
        return if (isNullOrEmpty()) false else all { Character.isDigit(it) }
    }
    private fun String.isUnaryMinusForStack(): Boolean =
        this == "-" && (postfixResult.lastOrNull().toString().isOperator() || postfixResult == "")
}

@ExperimentalStdlibApi
fun main() {
//    val input = "10 12\n5 6\n00"
    val input = "10abc\n\n"
    val inp = ByteArrayInputStream(input.toByteArray())
    System.setIn(inp)

    val sc = SmartCalculator()
    sc.multiSum()

    val infix = "8 * 3 +++ 12 * (4 --- 2)"
//    val infix = "1 - 2 * 3"
    val postfix=sc.toPostfix(infix)
    println(postfix)

    println(sc.sum(postfix))

}