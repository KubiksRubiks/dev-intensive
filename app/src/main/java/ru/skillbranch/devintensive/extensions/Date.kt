package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.extensions.Plurals.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

val Int.sec get() = this * TimeUnits.SECOND.size
val Int.min get() = this * TimeUnits.MINUTE.size
val Int.hour get() = this * TimeUnits.HOUR.size
val Int.day get() = this * TimeUnits.DAY.size

val Int.asPlurals
    get() = when {
        this % 10 == 1 -> ONE
        this % 10 in 2..4 -> FEW
        else -> MUCH
    }

val TimeUnits.pluralStrings
    get() = when (this) {
            TimeUnits.SECOND -> mapOf(ONE to "секунду", FEW to "секунды", MUCH to "секунд")
            TimeUnits.MINUTE -> mapOf(ONE to "минуту", FEW to "минуты", MUCH to "минут")
            TimeUnits.HOUR -> mapOf(ONE to "час", FEW to "часа", MUCH to "часов")
            TimeUnits.DAY -> mapOf(ONE to "день", FEW to "дня", MUCH to "дней")
        }

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    time += value * units.size
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    //val diff:Int = ((date.time + 200) / 1000 - (time + 200) / 1000) * 1000
    val tempTime = ((date.time + 200) / 1000 - (time + 200) / 1000) * 1000
    return if (tempTime >= 0) {
        when (tempTime) {
            in 0.sec..1.sec -> "только что"
            in 2.sec..45.sec -> "несколько секунд назад"
            in 46.sec..75.sec -> "минуту назад"
            in 76.sec..45.min -> {
                val totalValue = (tempTime / MINUTE).toInt()
                "${TimeUnits.MINUTE.plural(totalValue)} назад"
            }
            in 46.min..75.min -> "час назад"
            in 76.min..22.hour -> {
                val totalValue = (tempTime / HOUR).toInt()
                "${TimeUnits.HOUR.plural(totalValue)} назад"
            }
            in 23.hour..26.hour -> "день назад"
            in 27.hour..360.day -> {
                val totalValue = (tempTime / DAY).toInt()
                "${TimeUnits.DAY.plural(totalValue)} назад"
            }
            else -> "более года назад"
        }
    } else {
        when (tempTime) {
            in (-1).sec..0.sec -> "прямо сейчас"
            in (-45).sec..(-1).sec -> "через несколько секунд"
            in (-75).sec..(-45).sec -> "через минуту"
            in (-45).min..(-75).sec -> {
                val totalValue = (tempTime / MINUTE).toInt()
                "через ${TimeUnits.MINUTE.plural(totalValue)}"
            }
            in (-75).min..(-45).min -> "через час"
            in (-22).hour..(-75).min -> {
                val totalValue = (tempTime / HOUR).toInt()
                "через ${TimeUnits.HOUR.plural(totalValue)}"
            }
            in (-26).hour..(-22).hour -> "через день"
            in (-360).day..(-26).hour -> {
                val totalValue = (tempTime / DAY).toInt()
                "через ${TimeUnits.DAY.plural(totalValue)}"
            }
            else -> "более чем через год"
        }
    }
}

enum class TimeUnits(val size: Long) {
    SECOND(1000L),
    MINUTE(60 * SECOND.size),
    HOUR(60 * MINUTE.size),
    DAY(24 * HOUR.size);

    fun plural(value: Int) = "$value ${pluralStrings[value.asPlurals]}"
}

enum class Plurals{
    ONE,
    FEW,
    MUCH
}


