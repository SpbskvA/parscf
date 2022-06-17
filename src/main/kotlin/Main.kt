import org.jsoup.Jsoup
import java.io.BufferedReader
import java.io.InputStreamReader

fun main(args: Array<String>) {
    val l = listOf(
        Triple(1, "2-sat", "2-sat"),
        Triple(2, "meet-in-the-middle", "meet-in-the-middle"),
        Triple(3, "binary search", "binary%20search"),
        Triple(4, "bitmasks", "bitmasks"),
        Triple(5, "fft", "fft"),
        Triple(6, "geometry", "geometry"),
        Triple(7, "graphs", "graphs"),
        Triple(8, "two pointers", "two%20pointers"),
        Triple(9, "trees", "trees"),
        Triple(10, "dp", "dp"),
        Triple(11, "greedy", "greedy"),
        Triple(12, "games", "games"),
        Triple(13, "interactive", "interactive"),
        Triple(14, "chinese remainder theorem", "chinese%20remainder%20theorem"),
        Triple(15, "combinatorics", "combinatorics"),
        Triple(16, "combinatorics", "combinatorics"),
        Triple(17, "constructive algorithms", "constructive%20algorithms"),
        Triple(18, "shortest paths", "shortest%20paths"),
        Triple(19, "math", "math"),
        Triple(20, "matrices", "matrices"),
        Triple(21, "graph matchings", "graph%20matchings"),
        Triple(22, "brute", "graph%20matchings"),
        Triple(23, "dfs and similar", "dfs%20and%20similar"),
        Triple(24, "flows", "flows"),
        Triple(25, "expression parsing", "expression%20parsing"),
        Triple(26, "divide and conquer", "divide%20and%20conquer"),
        Triple(27, "schedules", "schedules"),
        Triple(28, "implementation", "implementation"),
        Triple(29, "dsu", "dsu"),
        Triple(30, "sortings", "sortings"),
        Triple(31, "strings", "strings"),
        Triple(32, "string suffix structures", "string%20suffix%20structures"),
        Triple(33, "data structures", "data%20structures"),
        Triple(34, "probabilities", "probabilities"),
        Triple(35, "number theory", "number%20theory"),
        Triple(36, "ternary search", "ternary%20search"),
        Triple(37, "hashing", "hashing")
    )
    for (nums in l) println("${nums.first}. ${nums.second}")
    println("Number of theme: ")
    val num = readInt()
    val theme = l[num - 1].third
    println("hard limites(ex. 800 3500) or only one num")
    val lvl = readInts()
    println("1. in ascending order\n2. in descending order")
    val choose = readInt()
    println("quanity of tasks (0 if all):")
    var sz = readInt()
    var s: String
    if (choose == 2) s = "BY_RATING_DESC" else s = "BY_RATING_ASC"
    val URL = "https://codeforces.com/problemset?order=$s&tags=$theme%2C${lvl[0]}-${lvl.last()}"
    val doc = Jsoup.connect(URL).get()
    val metaElements = doc.select("td.id").text().split(" ").toMutableList()
    val Elements = doc.select("span.ProblemRating").text().split(" ").toList()
    if (sz == 0) sz = Elements.size
    println("Name Url lvl:")
    for (i in 0 until sz) {
        var s = "codeforces.com/contest/"
        for (el in metaElements[i]) {
            if (!el.isDigit()) s += "/problem/"
            s += el
        }
        metaElements[i] = s
        val taskUrl = "https://${metaElements[i]}"
        val taskDoc = Jsoup.connect(taskUrl).get()
        val task = taskDoc.select("div.title").first()?.text()
        println("${task} ${metaElements[i]} ${Elements[i]}")
    }
    println("Exit?")
    val exit = readLine()
}

val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
fun readLine(): String = bufferedReader.readLine()!!
fun readInt(): Int = readLine().toInt()
fun readLong(): Long = readLine().toLong()
fun readLongs(): List<Long> = readLine().split(" ").map { it.toLong() }
fun readInts(): List<Int> = readLine().split(" ").map { it.toInt() }
fun readBoolean(): Boolean = readLine().toInt() == 1
fun readBooleans(): List<Boolean> = readLine().split(" ").map { it.toInt() == 1 }
fun booleanToInt(x: Boolean): Int = if (x) 1 else 0
fun intToBoolean(x: Int): Boolean = x == 1
fun readDouble(): Double = readLine().toDouble()
fun readDoubles(): List<Double> = readLine().split(" ").map { it.toDouble() }
inline operator fun <T> T.invoke(dummy: () -> Unit): T {
    dummy(); return this
}