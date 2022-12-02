import RPC.Companion.toOutcome
import RPC.Companion.toRPC

fun main() {
    Day02().all()
}

class Day02 : Day {

    override fun all() = solve {
        part1(8933)
        part2(11998)
    }

    private val part1 = report {
        map { (a, _, c) -> GameRPC(a.toRPC(), c.toRPC()) }
            .map { it.score() }
            .fold(0) { acc, cur -> acc + cur }
    }

    private val part2 = report {
        map { (a, _, c) -> GameRPC(a.toRPC(), c.toOutcome(a.toRPC())) }
            .map { it.score() }
            .fold(0) { acc, cur -> acc + cur }
    }

    private fun <R : Any> report(block: Sequence<CharArray>.() -> R) = setup(block) {
        map { it.toCharArray() }
    }

}

data class GameRPC(val opponent: RPC, val player: RPC) {
    fun score() = player.score + outcome()

    private fun outcome() = if (opponent == player) 3
    else when (opponent) {
        RPC.Rock -> if (player == RPC.Paper) 6 else 0
        RPC.Paper -> if (player == RPC.Scissors) 6 else 0
        RPC.Scissors -> if (player == RPC.Rock) 6 else 0
    }
}

enum class RPC(val score: Int) {
    Rock(1), Paper(2), Scissors(3);

    companion object {
        fun Char.toRPC() = when (this) {
            'A' -> Rock
            'B' -> Paper
            'C' -> Scissors
            'X' -> Rock
            'Y' -> Paper
            'Z' -> Scissors
            else -> throw RuntimeException("$this is not valid input")
        }

        fun Char.toOutcome(opponent: RPC) = when (this) {
            'X' -> when (opponent) {
                Rock -> Scissors
                Paper -> Rock
                Scissors -> Paper
            }

            'Y' -> opponent
            'Z' -> when (opponent) {
                Rock -> Paper
                Paper -> Scissors
                Scissors -> Rock
            }

            else -> throw RuntimeException("$this is not valid input")

        }
    }
}
