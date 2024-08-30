package com.mkiperszmid.parkitcourse.home.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CarEntity(
    @PrimaryKey
    val id: Long = 0,
    val latitude: Double,
    val longitude: Double
)
