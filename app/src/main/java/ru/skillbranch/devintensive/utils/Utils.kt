package ru.skillbranch.devintensive.utils

import ru.skillbranch.devintensive.extensions.trimOrNull

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? =
            if (fullName?.isNotEmpty() == true) fullName?.split(" ") else null

        val firstName = parts?.getOrNull(0)?.trimOrNull()
        val lastName = parts?.getOrNull(1)?.trimOrNull()


        return Pair(firstName, lastName)
    }

}
