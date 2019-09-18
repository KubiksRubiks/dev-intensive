package ru.skillbranch.devintensive.extensions

fun String.truncate(value: Int = 16): String {
    val appendix = "..."

    if (this.length > value) {
        var str = dropLast(this.length - value)
        return (str + appendix)
    } else {
        return this
    }
}

fun String.stripHtml(): String {
    return this
        .replace(Regex("<[^>]*>"), "") //удаляет HTML-теги
        .replace(Regex("\\s+"), " ") //удаляет пустые символы (пробелы) между словами если их больше 1
        .replace(Regex("[&'\"]"), "") //удаляет html escape последовательности ("& < > ' "")

}