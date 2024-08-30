package com.mkiperszmid.parkitcourse.home.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mkiperszmid.parkitcourse.home.data.local.entity.CarEntity

@Database(entities = [CarEntity::class], version = 1)
abstract class HomeDatabase : RoomDatabase() {
    abstract val dao: HomeDao
}