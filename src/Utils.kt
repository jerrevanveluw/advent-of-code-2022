import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("input", "$name.txt").readLines()

@OptIn(ExperimentalTime::class)
fun <R : Any, T : Any> Day.setup(block: T.() -> R, transform: Sequence<String>.() -> T) = { answer: R? ->
    measureTime {
        readInput("Day${day}").asSequence()
            .run(transform)
            .block()
            .log(answer)
    }.run { println("  In $inWholeMilliseconds ms") }
}

fun Any.log(correctAnswer: Any? = null) = println("  Answer: $this").also {
    correctAnswer?.let { check(this == it) }
}

interface Day {
    val day: String get() = javaClass.simpleName.substring(3)

    fun solve(parts: () -> Unit) {
        println("Day $day:")
        parts()
    }

    fun all()
}
