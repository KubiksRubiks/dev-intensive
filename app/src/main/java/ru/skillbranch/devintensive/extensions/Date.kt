package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

val Int.sec get() = this * TimeUnits.SECOND.size
val Int.min get() = this * TimeUnits.MINUTE.size
val Int.hour get() = this * TimeUnits.HOUR.size
val Int.day get() = this * TimeUnits.DAY.size

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val diff = ((date.time + 200) / 1000 - (time + 200) / 1000) * 1000

    return if (diff >= 0) {
        when (diff) {
            in 0.sec..1.sec -> "только что"
            in 2.sec..45.sec -> "несколько секунд назад"
            in 46.sec..75.sec -> "минуту назад"
            in 76.sec..45.min -> "${diff/ MINUTE} минут назад"
            in 46.min..75.min -> "час назад"
            in 76.min..22.hour -> "${diff/ HOUR} часов назад"
            in 23.hour..26.hour -> "день назад"
            in 27.hour..360.day -> "${diff/ DAY} дней назад"
            else -> "более года назад"
        }
    } else {
        when (diff) {
            in (-1).sec..0.sec -> "прямо сейчас"
            in (-45).sec..(-1).sec -> "через несколько секунд"
            in (-75).sec..(-45).sec -> "через минуту"
            in (-45).min..(-75).sec -> "через ${diff/ MINUTE} минут"
            in (-75).min..(-45).min -> "через час"
            in (-22).hour..(-75).min -> "через ${diff/ HOUR} часов назад"
            in (-26).hour..(-22).hour -> "через день"
            in (-360).day..(-26).hour -> "через ${diff/ DAY} дней назад"
            else -> "более чем через год"
        }
    }
}

enum class TimeUnits(val size: Long) {
    SECOND(1000L),
    MINUTE(60 * SECOND.size),
    HOUR(60 * MINUTE.size),
    DAY(24 * HOUR.size);
}

