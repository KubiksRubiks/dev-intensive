package ru.skillbranch.devintensive.utils

import java.util.*

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? =
            if (fullName?.isNotEmpty() == true) fullName?.split(" ") else null

        val firstName = parts?.getOrNull(0)?.trimOrNull()
        val lastName = parts?.getOrNull(1)?.trimOrNull()


        return Pair(firstName, lastName)
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        var charFirstName: String? = null
        var charLastName: String? = null

        charFirstName = firstName.trimOrNull()?.first()?.toString()?.toUpperCase(Locale("ru")) ?: ""
        charLastName = lastName.trimOrNull()?.first()?.toString()?.toUpperCase(Locale("ru")) ?: ""

        return if (charFirstName.isEmpty() && charLastName.isEmpty()) {
            null
        } else {
            "$charFirstName$charLastName"
        }
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val strBuilder = StringBuilder()
        payload.toCharArray().forEach {
            if(it.isWhitespace()){
                strBuilder.append(divider)
            } else{
                if(it.isUpperCase()){
                    strBuilder.append(
                        replaceRusToEngLetter(it.toLowerCase().toString())
                        .toUpperCase()
                    )
                } else {
                    strBuilder.append(replaceRusToEngLetter(it.toString()))
                }
            }
        }
        return strBuilder.toString()
    }

    fun String?.trimOrNull(): String? {
        val resultString = this?.trim()

        return if (resultString?.isEmpty() == true) null else resultString
    }

    private fun replaceRusToEngLetter(letter: String): String = when (letter) {
        "а" -> "a"
        "б" -> "b"
        "в" -> "v"
        "г" -> "g"
        "д" -> "d"
        "е" -> "e"
        "ё" -> "e"
        "ж" -> "zh"
        "з" -> "z"
        "и" -> "i"
        "й" -> "i"
        "к" -> "k"
        "л" -> "l"
        "м" -> "m"
        "н" -> "n"
        "о" -> "o"
        "п" -> "p"
        "р" -> "r"
        "с" -> "s"
        "т" -> "t"
        "у" -> "u"
        "ф" -> "f"
        "х" -> "h"
        "ц" -> "c"
        "ч" -> "ch"
        "ш" -> "sh"
        "щ" -> "sh'"
        "ъ" -> ""
        "ы" -> "i"
        "ь" -> ""
        "э" -> "e"
        "ю" -> "yu"
        "я" -> "ya"
        else -> letter
    }
}
