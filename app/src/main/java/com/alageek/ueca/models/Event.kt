package com.alageek.ueca.models

import android.os.Parcelable
import androidx.compose.runtime.saveable.listSaver
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Event(var description: String = "", var time: Date) : Parcelable

val EventSaver = listSaver<Event, Any>(
    save = { listOf(it.description, it.time) },
    restore = { Event(it[0] as String, it[1] as Date) }
)
