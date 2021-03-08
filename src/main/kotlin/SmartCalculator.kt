import java.io.ByteArrayInputStream
import java.lang.ArithmeticException
import java.lang.Exception
import java.util.*
import kotlin.math.pow

@ExperimentalStdlibApi
class SmartCalculator {
    private var variables = mutableMapOf<String, Int>()
    var postfixResult = ""

    private val operatorPriority= mapOf(
        "(" to 3,
        ")" to 3,
        "^" to 2,
        "*" to 1,
        "/" to 1,
        "-" to 0,
        "+" to 0
    )


    fun sum(input: String) {
        try {
            val postfix = toPostfix(input)

            val sumOfInput= sumOfPostfix(postfix)

            println(sumOfInput)
        } catch (e: Exception) {
            println(e.message)
        }
    }

    @ExperimentalStdlibApi
    fun multiSum() {
        val scanner = Scanner(System.`in`)

        while (scanner.hasNextLine()) {
            val nextLine = scanner.nextLine()

            val line = nextLine.toString().trim()
            if (line == "") continue

            try {

                when {
                    line.first() == '/' -> if (!runCommand(line)) return
                    line.contains('=') -> variableAssignment(line)
                    line.isAVariable() -> printVariable(line)
                    else -> sum(line)
                }
            } catch (e: Exception) {
                println(e.message)
                continue
            }
        }

    }

    private fun subtract(a:Int, b:Int):Int = a - b
    private fun divide(a:Int, b:Int):Int = a / b
    private fun power(a:Int, b:Int):Int = a.toDouble().pow(b.toDouble()).toInt()

    @ExperimentalStdlibApi
    fun MutableList<Int>.performOperation(operator:String):Int {
        try {
            return when (operator) {
                "*" -> removeLast() * removeLast()
                "/" -> divide(b=removeLast() , a= removeLast())
                "^" -> power(b=removeLast() , a= removeLast())
                "-" -> {
                    if (size == 1) - removeLast()
                    else subtract(b=removeLast(), a=removeLast())
                }
                "+" -> removeLast() + removeLast()

                else -> throw InvalidExpressionException()
            }
        } catch (e :Exception){
            throw InvalidExpressionException()
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
                        else throw UnknownVariableException()
                    }
                    element.isOperator() -> {
                        stack.add(stack.performOperation(element))
                    }
                    else -> stack.add(element.toInt())
                }
            }

        return stack.last()

    }

    @ExperimentalStdlibApi
    private fun MutableList<String>.popToPostfixResult():String{
        val last = removeLast()
        postfixResult += "$last "
        return last
    }

    private fun MutableList<String>.nextHasHigherPrecedenceThan(operator :String):Boolean {
        val topOfStackPriority = (operatorPriority[this.last()] ?: error("")).toInt()
        val incomingOperatorPriority = (operatorPriority[operator] ?: error("")).toInt()

        return topOfStackPriority >= incomingOperatorPriority
    }

    @ExperimentalStdlibApi
    fun toPostfix(input: String):String {
        postfixResult = ""
        val stack = mutableListOf<String>()

       input.toMathList()
            .forEach{element ->
                when {
                    (element.isAVariable() || element.isNumber()) ->
                        postfixResult += "$element "
                    element.isOperator() ->
                        when {

                            element.isUnaryMinusOnResult() -> postfixResult += element

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
                    else -> throw InvalidExpressionException()
                }
            }

        while (stack.size > 0){
            if (stack.popToPostfixResult() == "(") throw InvalidExpressionException()
        }
        return postfixResult
    }


    private fun variableAssignment(line: String) {

        val (i, v) = line.split("=").map { it.trim() }

        try {
            if (i.isAVariable()){
                if (variables.containsKey(v)) variables[i] = variables[v]!!.toInt()
                else{
                    if (v.isNumber()) {
                        variables[i] = v.toInt()
                    }
                    else throw InvalidAssignmentException()
                }
            }else throw InvalidAssignmentException()

        }catch (e: Exception){
            println(e.message)
        }

    }


    private fun String.toMathList():List<String> {
        val reg = Regex("(?<=$alphaNumeric)(?=$operatorGroupMatcher)|(?<=$operatorGroupMatcher)(?=$alphaNumeric)")

        return split(reg)
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
            .filterNot{it == ""}

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
            else -> throw UnknownCommandException()
        }

        return true

    }

    private fun printVariable(name: String) {
        if (variables.containsKey(name)) println(variables[name])
        else println("Unknown variable")
    }

    private val operatorMatcher = "[-+()*/^=]"
    private val operatorGroupMatcher = "[-+()*/^= ]"
    private val variableMatcher = "[a-zA-Z]"
    private val alphaNumeric = "[0-9a-zA-Z]"

    private fun String.isAVariable(): Boolean = matches(Regex("$variableMatcher+"))
    private fun String.isOperator(): Boolean = matches(Regex("$operatorMatcher+"))
    private fun String.isOperatorGroup(): Boolean = matches(Regex("$operatorGroupMatcher+"))
    private fun String.isNumber() : Boolean =  matches(Regex("-?[0-9]+"))
    private fun String.isUnaryMinusOnResult(): Boolean =
        this == "-" && (postfixResult.lastOrNull().toString().isOperator() || postfixResult == "")

    private fun String.isMinusUnaryOperator(): Boolean = toSet().size == 1 && toSet().first() == '-'
    private fun String.isPositiveUnaryOperator(): Boolean = toSet().size == 1 && toSet().first() == '+'

    class UnknownCommandException(message: String = "Unknown command") : Exception(message)
    class InvalidExpressionException(message: String = "Invalid expression") : Exception(message)
    class UnknownVariableException(message: String = "Unknown variable") : Exception(message)
    class InvalidAssignmentException(message: String = "Invalid assignment") : Exception(message)
}

class UnknownVariable {

}

@ExperimentalStdlibApi
fun main() {
//    val input = "10 12\n5 6\n00"
    var input = "10abc\n\n"
    var inp = ByteArrayInputStream(input.toByteArray())
    System.setIn(inp)

    var sc = SmartCalculator()
    sc.multiSum()

    input = "h = -10 \n h\n"
    inp = ByteArrayInputStream(input.toByteArray())
    System.setIn(inp)

    sc = SmartCalculator()
    sc.multiSum()

    val infix = "8 * 3 +++ 12 * (4 --- 2)"
    try {
        sc = SmartCalculator()
        val postfix=sc.toPostfix(infix)
        println(postfix)

        println(sc.sumOfPostfix(postfix))

    }catch(e:Exception){
        println("Unknown variable")
    }

    input = "(-1 - 2) * 3"

    sc = SmartCalculator()
    val postfix2 = sc.toPostfix(input)
    println(postfix2)

    input = "10+"
    val sum =sc.sum(input)
    println(sum)

}