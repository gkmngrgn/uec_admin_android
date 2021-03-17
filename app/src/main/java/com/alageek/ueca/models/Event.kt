package com.alageek.ueca.models

import android.os.Parcelable
import androidx.compose.runtime.saveable.Saver
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(var description: String = "") : Parcelable

val EventSaver = Saver<Event, String>(
    save = { it.description },
    restore = { Event(it) }
)
