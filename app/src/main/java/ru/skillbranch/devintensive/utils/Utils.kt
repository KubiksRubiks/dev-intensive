package ru.skillbranch.devintensive.utils

import ru.skillbranch.devintensive.extensions.trimOrNull
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

        return if(charFirstName.isEmpty() && charLastName.isEmpty()){
            null
        } else {
            "$charFirstName$charLastName"
        }
    }
}
