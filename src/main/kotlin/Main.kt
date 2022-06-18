import org.jsoup.Jsoup
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.file.Paths

var problemsFile = File("")
var chooseWrite = 2

fun main(args: Array<String>) {
    val themesList = mutableListOf<Triple<String, String, String>>()
    val readThemes: InputStream = File("resources/themes.txt").inputStream()
    val htmlList = mutableListOf<String>()
    readThemes.bufferedReader().forEachLine {htmlList.add(it)}

    for (htmlString in htmlList) {
        val problem = htmlString.replace("\"", "")
        val themeList = problem.split(", ")
        themesList.add(Triple(themeList[0], themeList[1], themeList[2]))
    }
    for (nums in themesList) println("${nums.first}. ${nums.second}")

    println("Numbers of themes (wrtite with spase): ")
    val chooseThemes = readInts()
    var themeUrl = ""
    for (chooseTheme in chooseThemes) themeUrl += themesList[chooseTheme - 1].third + ","

    println("Hard limits of problems(ex. 800 3500) or only one num")
    val HardOfProblems = readInts().toMutableList()
    if (HardOfProblems.size == 1) HardOfProblems.add(3500)

    println("1. in ascending order\n2. in descending order")
    val chooseOrder = readInt()

    val s: String
    if (chooseOrder == 2) s = "BY_RATING_DESC" else s = "BY_RATING_ASC"
    val problemsUrl = "https://codeforces.com/problemset?order=$s&tags=$themeUrl%2C${HardOfProblems.first()}-${HardOfProblems.last()}"
    val cfHtml = Jsoup.connect(problemsUrl).get()
    val nameElements = cfHtml.select("td.id").text().split(" ").toMutableList()
    val rateElements = cfHtml.select("span.ProblemRating").text().split(" ").toList()

    println("Quanity of problems (0 if all):")
    var problemsQuanity = readInt()

    println("1. Write in concole\n2. Save in txt")
    chooseWrite = readInt()
    chooseTxt(chooseWrite)

    val problemsFound = rateElements.size
    problemsQuanity = isEnough(problemsQuanity, problemsFound)

    for (i in 0 until problemsQuanity) {
        nameElements[i] = makeUrl(nameElements[i])
        try {
            val taskUrl = "https://${nameElements[i]}"
            val problemHtml = Jsoup.connect(taskUrl).get()
        }
        catch (isProblem: org.jsoup.HttpStatusException) {
            println("Problems aren`t find. You can try change hard limits or themes...")
            break
        }
        val taskUrl = "https://${nameElements[i]}"
        val problemHtml = Jsoup.connect(taskUrl).get()
        val problemName = problemHtml.select("div.title").first()?.text()
        if (chooseWrite == 2) {
            problemsFile.appendText("${problemName} ${nameElements[i]} ${rateElements[i]}\n")
        }
        else {
            println("${problemName} ${nameElements[i]} ${rateElements[i]}")
        }
    }

    println("If you want to exit write something...")
    val isExit = readLine()
}

fun isEnough(problemsQuanity: Int, problemsFound: Int): Int{
    var problemsQuanityCopy = problemsQuanity
    if (problemsQuanityCopy == 0) problemsQuanityCopy = problemsFound
    else {
        if (problemsFound < problemsQuanityCopy) {
            if (problemsFound <= 1) println("We find only $problemsFound page")
            else println("We find only $problemsFound pages")
        }
        problemsQuanityCopy = minOf(problemsFound, problemsQuanity)
    }
    return problemsQuanityCopy
}

fun makeUrl(nameProblem: String): String {
    var problemUrl = "codeforces.com/contest/"
    for (name in nameProblem) {
        if (!name.isDigit()) problemUrl += "/problem/"
        problemUrl += name
    }
    return problemUrl
}

fun chooseTxt(chooseWrite: Int) {
    if (chooseWrite == 2) {
        val workPath = Paths.get("").toAbsolutePath().toString()
        val problemsDirectory = File("$workPath\\problemsList")
        problemsDirectory.mkdir()
        println("Txt will be in $problemsDirectory")
        problemsFile = File("problemsList/problemsList.txt")
        problemsFile.createNewFile()
    }
}

val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
fun readLine(): String = bufferedReader.readLine()!!
fun readInt(): Int = readLine().toInt()
fun readInts(): List<Int> = readLine().split(" ").map { it.toInt() }