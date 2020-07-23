package com.raywenderlich.android.starsync.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class People(@PrimaryKey val name: String,
    val height: String?, val mass: String?, val hair_color: String?, val skin_color: String?,
    val eye_color: String?, val gender: String?)