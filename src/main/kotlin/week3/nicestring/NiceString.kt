package week3.nicestring

fun containsSubstrings(s: String): Boolean {
    val q = setOf("bu", "ba", "be")

    return q.any { s.contains(it) }
}

fun containsVowel(s: String): Boolean {
    val vowels = setOf('a', 'e', 'i', 'o', 'u')

    return s.filter { vowels.contains(it) }.length >= 3
}

fun containsDoubleLetter(s: String): Boolean {
    val rgx = "(\\w)\\1+".toRegex()

    return rgx.containsMatchIn(s)
}

fun String.isNice(): Boolean {
    return listOf(
            !containsSubstrings(this),
            containsVowel(this),
            containsDoubleLetter(this)
    ).filter { it }.size >= 2
}