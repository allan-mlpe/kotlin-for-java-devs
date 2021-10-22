package week2.mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    val commonLetters = secret.map { it }
        .distinct()
        .sumBy { ch -> Math.min(secret.count { it == ch }, guess.count { it == ch} ) }

    val letterInCorrectPosition = secret.zip(guess)
        .count { it.first == it.second }

    return Evaluation(letterInCorrectPosition, commonLetters - letterInCorrectPosition)
}

fun main() {
    println(evaluateGuess("ABCD", "ABCD"))
    println(evaluateGuess("ABCD", "ABDC"))
    println(evaluateGuess("ABCD", "DCBA"))
    println(evaluateGuess("AABC", "ADFE"))
    println(evaluateGuess("AABC", "ADFA"))
    println(evaluateGuess("AABC", "DFAA"))
    println(evaluateGuess("AABC", "DEFA"))
}