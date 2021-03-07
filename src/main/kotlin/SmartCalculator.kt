import java.io.ByteArrayInputStream
import java.util.*

class SmartCalculator {

    fun sum(nums: IntArray) {

        val sumOfNums = nums.reduce{sumNum, num -> sumNum + num}
        println(sumOfNums)
    }

    fun multiSum() {
        val scanner = Scanner(System.`in`)

        while (scanner.hasNextLine()){
            val nextLine = scanner.nextLine()

            when (val line = nextLine.toString()){
                "/exit" -> {
                    println("Bye!" )
                    return
                }
                "/help" -> {
                    println("The program calculates the sum of numbers" )
                    return
                }
                else -> {
                    val numbers  =  "$line 0"
                        .split(' ')
                        .map{it.toInt()}
                        .toIntArray()

                    sum(numbers)

                }
            }
        }

    }
}

fun main () {
    val input = "10 12\n5 6\n00"
    val inp = ByteArrayInputStream(input.toByteArray())
    System.setIn(inp)

    val sc = SmartCalculator()
    sc.multiSum()

}