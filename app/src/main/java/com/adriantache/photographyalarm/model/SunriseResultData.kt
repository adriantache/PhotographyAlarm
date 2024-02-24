package com.adriantache.photographyalarm.model

import androidx.annotation.DrawableRes
import com.adriantache.photographyalarm.data.LocalTimeAsLongSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

@Serializable
class SunriseResultData(
    val time: String,
    @Serializable(LocalTimeAsLongSerializer::class) val timestamp: LocalTime,
    @DrawableRes val iconRes: Int,
) {
    constructor(time: LocalDateTime, @DrawableRes iconRes: Int) : this(
        timestamp = time.toLocalTime().truncatedTo(ChronoUnit.MINUTES),
        time = time.toLocalTime().truncatedTo(ChronoUnit.MINUTES).toString(),
        iconRes = iconRes,
    )
}
