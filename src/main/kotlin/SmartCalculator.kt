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
            val line = scanner.nextLine()

            if (line.toString() == "/exit") {
                println("Bye!" )
                return
            }

            if (line != ""){
                val (x,y)  =  "$line 0"
                    .split(' ')
                    .map{it.toInt()}

                sum(intArrayOf(x,y))
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