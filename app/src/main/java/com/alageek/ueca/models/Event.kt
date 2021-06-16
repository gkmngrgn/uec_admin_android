package com.alageek.ueca.models

import android.os.Parcelable
import androidx.compose.runtime.saveable.listSaver
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Event(var description: String = "", var time: Date, var timezones: List<Int>) :
    Parcelable {
    fun getTimes(): List<Int> {
        val times = listOf<Int>()
        /* TODO */
        return times
    }
}

val EventSaver = listSaver<Event, Any>(
    save = { listOf(it.description, it.time, it.timezones) },
    restore = { Event(it[0] as String, it[1] as Date, (it[2] as List<*>).filterIsInstance<Int>()) }
)
