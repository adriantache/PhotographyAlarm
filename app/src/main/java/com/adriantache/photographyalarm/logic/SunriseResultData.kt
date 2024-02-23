package com.adriantache.photographyalarm.logic

import androidx.annotation.DrawableRes
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class SunriseResultData(
    val time: String,
    val timestamp: LocalTime,
    @DrawableRes val iconRes: Int,
) {
    constructor(time: LocalDateTime, @DrawableRes iconRes: Int) : this(
        timestamp = time.toLocalTime().truncatedTo(ChronoUnit.MINUTES),
        time = time.toLocalTime().truncatedTo(ChronoUnit.MINUTES).toString(),
        iconRes = iconRes,
    )
}
