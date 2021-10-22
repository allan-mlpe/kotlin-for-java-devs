package week2.mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun findIndexes(word: String, letter: Char): MutableList<Int> {
    val indexes = mutableListOf<Int>()

    for(i in word.indices) {
        if(word[i] == letter) {
            indexes.add(i)
        }
    }

    return indexes
}

fun computeResult(x: Map<Char, List<Int>>, y: Map<Char, List<Int>>): Evaluation {
    var wrongs = 0
    var rights = 0

    val keys = x.keys

    for(key in keys) {
     val xIndexes = x.getValue(key)
        val yIndexes = y.getValue(key)

        val smallerList = if (xIndexes.size <= yIndexes.size) xIndexes else yIndexes
        val biggerList = if (xIndexes.size > yIndexes.size) xIndexes else yIndexes

        for(i in smallerList) {
            if (biggerList.contains(i)) {
                rights++
            } else {
                wrongs++
            }
        }
    }

    return Evaluation(rights, wrongs)
}

fun evaluateGuess(secret: String, guess: String): Evaluation {
    val secretLength = secret.length;

    val commonLetters = mutableListOf<Char>()

    for (i in 0 until secretLength) {
        val secretChar: Char = secret[i]

        if (guess.contains(secretChar)) {
            commonLetters.add(secretChar);
        }
    }

    val secretIndexes = commonLetters.map { it to findIndexes(secret, it) }.toMap()
    val guessIndexes = commonLetters.map { it to findIndexes( guess, it) }.toMap()


    return computeResult(secretIndexes, guessIndexes)
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