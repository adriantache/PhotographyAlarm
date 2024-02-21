package com.adriantache.photographyalarm.api

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.TimeZone

fun parseDate(time: String): LocalTime? {
    val adjustedTime = if (time.indexOf(":") != 2) "0$time" else time
    val pattern = "hh:mm:ss a";

    return LocalTime.parse(adjustedTime, DateTimeFormatter.ofPattern(pattern))
}

fun parseDate(
    timeString: String,
    timezoneString: String,
    date: LocalDate,
): LocalDateTime? {
    val time = parseDate(timeString) ?: return null
    val timezone = TimeZone.getTimeZone(timezoneString)
    val zonedDateTime = date.atTime(time).atZone(timezone.toZoneId())

    return zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
}

fun parseTimestamp(timestamp: Long): LocalDateTime? {
    val zonedDateTime = Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault())

    return zonedDateTime.toLocalDateTime()
}
