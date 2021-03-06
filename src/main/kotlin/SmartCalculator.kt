import java.util.*

class SmartCalculator {
    fun sum(): Int{
        val scanner = Scanner(System.`in`)

        val a = scanner.nextInt()
        val b = scanner.nextInt()

        return a + b
    }
}