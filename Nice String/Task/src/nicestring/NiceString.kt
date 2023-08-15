package nicestring

fun String.isNice(): Boolean {
    val vowels = listOf('a', 'e', 'i', 'o', 'u')

    val notContains = !this.contains("bu") && !this.contains("ba") && !this.contains("be")
    val containsThreeVowels = this.filter { it -> vowels.contains(it) }.count() >= 3
    val containsDoubleLetter = this.zipWithNext().any {it.first == it.second}

    return listOf(notContains, containsThreeVowels, containsDoubleLetter).count { it } >= 2
}