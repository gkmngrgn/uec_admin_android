package com.alageek.ueca.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(var description: String) : Parcelable
