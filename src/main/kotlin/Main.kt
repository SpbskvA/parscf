import org.jsoup.Jsoup
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader

fun main(args: Array<String>) {
    val themesList = mutableListOf<Triple<String, String, String>>()
    val readThemes: InputStream = File("themes.txt").inputStream()
    val lineList = mutableListOf<String>()
    readThemes.bufferedReader().forEachLine {lineList.add(it)}
    for (item in lineList) {
        val theme = item.replace("\"", "")
        val themeList = theme.split(", ")
        themesList.add(Triple(themeList[0], themeList[1], themeList[2]))
    }
    for (nums in themesList) println("${nums.first}. ${nums.second}")
    println("Numbers of themes (with spase): ")
    val chooseThemes = readInts()
    var themeUrl = ""
    for (chooseTheme in chooseThemes) themeUrl += themesList[chooseTheme - 1].third + ","
    println("Hard limites(ex. 800 3500) or only one num")
    val HardOfProblems = readInts().toMutableList()
    if (HardOfProblems.size == 1) HardOfProblems.add(3500)
    println("1. in ascending order\n2. in descending order")
    val chooseOrder = readInt()
    println("quanity of tasks (0 if all):")
    var problemsQuanity = readInt()
    val s: String
    if (chooseOrder == 2) s = "BY_RATING_DESC" else s = "BY_RATING_ASC"
    val problemsUrl = "https://codeforces.com/problemset?order=$s&tags=$themeUrl%2C${HardOfProblems.first()}-${HardOfProblems.last()}"
    val cfHtml = Jsoup.connect(problemsUrl).get()
    val nameElements = cfHtml.select("td.id").text().split(" ").toMutableList()
    val rateElements = cfHtml.select("span.ProblemRating").text().split(" ").toList()
    val problemsFound = rateElements.size
    if (problemsQuanity == 0) problemsQuanity = problemsFound
    else {
        if (problemsFound < problemsQuanity) {
            if (problemsFound <= 1) println("We just find $problemsFound page")
            else println("We just find $problemsFound pages")
        }
        problemsQuanity = minOf(problemsFound, problemsQuanity)
    }
    println("Name Url lvl:")
    for (i in 0 until problemsQuanity) {
        var problemUrl = "codeforces.com/contest/"
        for (el in nameElements[i]) {
            if (!el.isDigit()) problemUrl += "/problem/"
            problemUrl += el
        }
        nameElements[i] = problemUrl
        try {
            val taskUrl = "https://${nameElements[i]}"
            val problemHtml = Jsoup.connect(taskUrl).get()
        }
        catch (isProblem: org.jsoup.HttpStatusException) {
            println("Tasks aren`t find. You can try change limits...")
            break
        }
        val taskUrl = "https://${nameElements[i]}"
        val problemHtml = Jsoup.connect(taskUrl).get()
        val problemName = problemHtml.select("div.title").first()?.text()
        println("${problemName} ${nameElements[i]} ${rateElements[i]}")
    }
    println("If you want to exit print something")
    val isExit = readLine()
}

val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
fun readLine(): String = bufferedReader.readLine()!!
fun readInt(): Int = readLine().toInt()
fun readInts(): List<Int> = readLine().split(" ").map { it.toInt() }