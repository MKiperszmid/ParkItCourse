package com.mkiperszmid.parkitcourse.home.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkiperszmid.parkitcourse.home.data.local.entity.CarEntity

@Dao
interface HomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCar(car: CarEntity)

    @Query("SELECT * FROM CarEntity")
    suspend fun getParkedCar(): CarEntity?

    @Delete
    suspend fun deleteCar(car: CarEntity)
}